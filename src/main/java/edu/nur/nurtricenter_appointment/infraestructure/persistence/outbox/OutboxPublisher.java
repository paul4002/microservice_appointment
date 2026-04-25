package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Component
public class OutboxPublisher {
	private static final Logger logger = LoggerFactory.getLogger(OutboxPublisher.class);
	private static final String FORCED_EXCHANGE = "outbox.events";
	private final OutboxEventRepository repository;
	private final RabbitTemplate rabbitTemplate;
	private final RabbitAdmin rabbitAdmin;
	private final OutboxPublisherProperties props;
	private final ObjectMapper objectMapper;
	private final Set<String> declaredEventQueues = ConcurrentHashMap.newKeySet();

	public OutboxPublisher(
		OutboxEventRepository repository,
		RabbitTemplate rabbitTemplate,
		RabbitAdmin rabbitAdmin,
		OutboxPublisherProperties props,
		ObjectMapper objectMapper
	) {
		this.repository = repository;
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitAdmin = rabbitAdmin;
		this.props = props;
		this.objectMapper = objectMapper;
	}

	@Scheduled(fixedDelayString = "${rabbitmq.outbox-poll-interval-ms:1000}")
	public void publishPending() {
		List<OutboxEventEntity> events = repository.findByProcessedOnIsNullAndNextAttemptAtLessThanEqualOrderByOccurredOnAsc(
			LocalDateTime.now(),
			PageRequest.of(0, props.getOutboxBatchSize())
		);
		for (OutboxEventEntity event : events) {
			publishSingle(event);
		}
	}

	@Transactional
	protected void publishSingle(OutboxEventEntity event) {
		try {
			String routingKey = resolveRoutingKey(event);
			String envelope = buildEnvelope(event);
			ensureEventQueueBinding(routingKey);
			rabbitTemplate.convertAndSend(FORCED_EXCHANGE, routingKey, envelope, msg -> {
				msg.getMessageProperties().setContentType("application/json");
				return msg;
			});
			event.setProcessedOn(LocalDateTime.now());
			event.setLastError(null);
			repository.save(event);
			logger.info("✓ Evento enviado a RabbitMQ - Evento: {}, Queue: {}, Exchange: {}",
				event.getEventName(), routingKey, FORCED_EXCHANGE);
		} catch (Exception ex) {
			int attempts = event.getAttempts() != null ? event.getAttempts() + 1 : 1;
			event.setAttempts(attempts);
			event.setLastError(ex.getMessage());
			long baseBackoffMs = props.getPublishBackoffMs();
			int cap = props.getPublishRetries() > 0 ? props.getPublishRetries() : 10;
			long delayMs = baseBackoffMs * Math.min(attempts, cap);
			event.setNextAttemptAt(LocalDateTime.now().plusNanos(delayMs * 1_000_000));
			repository.save(event);
			logger.warn("⚠ Error al enviar evento a RabbitMQ - Evento: {}, Intento: {}, Error: {}, Próximo intento en: {}ms",
				event.getEventName(), attempts, ex.getMessage(), delayMs);
		}
	}

	private void ensureEventQueueBinding(String queueName) {
		if (queueName == null || queueName.isBlank()) {
			return;
		}
		if (!declaredEventQueues.add(queueName)) {
			return;
		}

		try {
			Queue queue = QueueBuilder.durable(queueName).build();
			Exchange exchange = buildExchange();
			rabbitAdmin.declareExchange(exchange);
			rabbitAdmin.declareQueue(queue);
			rabbitAdmin.declareBinding(buildBinding(queue, exchange, queueName));
		} catch (Exception ex) {
			declaredEventQueues.remove(queueName);
		}
	}

	private Exchange buildExchange() {
		String type = props.getExchangeType() != null ? props.getExchangeType().toLowerCase() : "fanout";
		return switch (type) {
			case "topic" -> ExchangeBuilder.topicExchange(FORCED_EXCHANGE).durable(props.isExchangeDurable()).build();
			case "direct" -> ExchangeBuilder.directExchange(FORCED_EXCHANGE).durable(props.isExchangeDurable()).build();
			case "fanout" -> ExchangeBuilder.fanoutExchange(FORCED_EXCHANGE).durable(props.isExchangeDurable()).build();
			default -> ExchangeBuilder.fanoutExchange(FORCED_EXCHANGE).durable(props.isExchangeDurable()).build();
		};
	}

	private Binding buildBinding(Queue queue, Exchange exchange, String bindingKey) {
		if (exchange instanceof FanoutExchange fanoutExchange) {
			return BindingBuilder.bind(queue).to(fanoutExchange);
		}
		if (exchange instanceof TopicExchange topicExchange) {
			return BindingBuilder.bind(queue).to(topicExchange).with(bindingKey);
		}
		if (exchange instanceof DirectExchange directExchange) {
			return BindingBuilder.bind(queue).to(directExchange).with(bindingKey);
		}
		return BindingBuilder.bind(queue).to(exchange).with(bindingKey).noargs();
	}

	private String resolveRoutingKey(OutboxEventEntity event) {
		if (event.getRoutingKey() != null && !event.getRoutingKey().isBlank()) {
			return event.getRoutingKey();
		}
		if (event.getEventName() != null && event.getEventName().contains(".")) {
			return event.getEventName();
		}
		if (event.getEventType() != null && event.getEventType().contains(".")) {
			return event.getEventType();
		}
		return props.getRoutingKey() != null ? props.getRoutingKey() : "";
	}

	private String buildEnvelope(OutboxEventEntity event) {
		String eventName = resolveEventName(event);
		String occurredOn = resolveOccurredOn(event);
		UUID eventId = event.getEventId() != null ? event.getEventId() : UUID.randomUUID();
		UUID correlationId = event.getCorrelationId() != null ? event.getCorrelationId() : UUID.randomUUID();
		int schemaVersion = event.getSchemaVersion() != null ? event.getSchemaVersion() : 1;
		JsonNode payload = parsePayload(event.getPayload());

		Envelope envelope = new Envelope(
			eventId.toString(),
			eventName,
			eventName,
			occurredOn,
			schemaVersion,
			correlationId.toString(),
			event.getAggregateId(),
			payload
		);

		try {
			return objectMapper.writeValueAsString(envelope);
		} catch (JsonProcessingException ex) {
			throw new IllegalStateException("Failed to serialize outbox envelope", ex);
		}
	}

	private String resolveEventName(OutboxEventEntity event) {
		if (event.getEventName() != null && !event.getEventName().isBlank() && event.getEventName().contains(".")) {
			return event.getEventName();
		}
		if (event.getRoutingKey() != null && !event.getRoutingKey().isBlank()) {
			return event.getRoutingKey();
		}
		if (event.getEventName() != null && !event.getEventName().isBlank()) {
			return event.getEventName();
		}
		if (event.getEventType() != null && !event.getEventType().isBlank()) {
			return event.getEventType();
		}
		return "UnknownEvent";
	}

	private String resolveOccurredOn(OutboxEventEntity event) {
		LocalDateTime occurredOn = event.getOccurredOn() != null ? event.getOccurredOn() : LocalDateTime.now(ZoneOffset.UTC);
		return occurredOn.toInstant(ZoneOffset.UTC).toString();
	}

	private JsonNode parsePayload(String payload) {
		try {
			return payload == null ? objectMapper.nullNode() : objectMapper.readTree(payload);
		} catch (JsonProcessingException ex) {
			throw new IllegalStateException("Failed to parse outbox payload", ex);
		}
	}

	private record Envelope(
		String event_id,
		String event_name,
		String event,
		String occurred_on,
		int schema_version,
		String correlation_id,
		String aggregate_id,
		JsonNode payload
	) {}
}

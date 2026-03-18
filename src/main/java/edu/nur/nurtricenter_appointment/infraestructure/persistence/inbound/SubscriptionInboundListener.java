package edu.nur.nurtricenter_appointment.infraestructure.persistence.inbound;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import an.awesome.pipelinr.Pipeline;
import edu.nur.nurtricenter_appointment.application.subscriptions.processPatientCreatedEvent.ProcessPatientCreatedEventCommand;
import edu.nur.nurtricenter_appointment.core.results.Result;

@Component
@ConditionalOnProperty(name = "inbound.rabbitmq.enabled", havingValue = "true", matchIfMissing = true)
public class SubscriptionInboundListener {
	private static final Logger log = LoggerFactory.getLogger(SubscriptionInboundListener.class);

	private final Pipeline pipeline;
	private final ObjectMapper objectMapper;

	public SubscriptionInboundListener(Pipeline pipeline, ObjectMapper objectMapper) {
		this.pipeline = pipeline;
		this.objectMapper = objectMapper;
	}

	@RabbitListener(bindings = @QueueBinding(
		value = @Queue(value = "${inbound.rabbitmq.queue:citas-evaluaciones.inbound}"),
		exchange = @Exchange(value = "${inbound.rabbitmq.exchange}", type = "topic"),
		key = "${inbound.rabbitmq.routing-keys:paciente.paciente-creado}"
	))
	public void onMessage(Message message) {
		try {
			String body = new String(message.getBody());
			Map<String, Object> parsed = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});

			String eventName = readString(parsed, "event", "event_name", "eventName");

			Map<String, Object> payload = extractPayload(parsed);

			Result result = Result.success();
			if ("paciente.paciente-creado".equals(eventName)) {
				result = new ProcessPatientCreatedEventCommand(payload).execute(pipeline);
			}
			if (result.isFailure()) throw new Exception(result.getError().getDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Inbound subscription message parse failure", ex);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> extractPayload(Map<String, Object> parsed) {
		Object payload = parsed.get("payload");
		if (payload instanceof Map<?, ?> mapPayload) {
			return (Map<String, Object>) mapPayload;
		}
		return parsed;
	}

	private String readString(Map<String, Object> values, String... keys) {
		for (String key : keys) {
			Object value = values.get(key);
			if (value instanceof String str && !str.isBlank()) {
				return str;
			}
		}
		return null;
	}
}
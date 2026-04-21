package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("OutboxPublisher Tests")
class OutboxPublisherTest {

	private OutboxPublisher outboxPublisher;

	@Mock
	private OutboxEventRepository repository;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Mock
	private RabbitAdmin rabbitAdmin;

	private OutboxPublisherProperties properties;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		properties = new OutboxPublisherProperties();
		properties.setExchangeType("fanout");
		properties.setExchangeDurable(true);
		properties.setPublishRetries(3);
		properties.setPublishBackoffMs(250);
		properties.setOutboxBatchSize(50);
		properties.setRoutingKey("default.routing.key");

		objectMapper = new ObjectMapper();
		outboxPublisher = new OutboxPublisher(repository, rabbitTemplate, rabbitAdmin, properties, objectMapper);
	}

	// ===== publishPending Tests =====

	@Test
	@DisplayName("Should publish all pending events when available")
	void testPublishPending_WithPendingEvents() {
		// Arrange
		OutboxEventEntity event1 = createEvent("test.event.1", "test.event.1", "aggregate-1");
		OutboxEventEntity event2 = createEvent("test.event.2", "test.event.2", "aggregate-2");
		List<OutboxEventEntity> pendingEvents = List.of(event1, event2);

		when(repository.findByProcessedOnIsNullAndNextAttemptAtLessThanEqualOrderByOccurredOnAsc(
			any(LocalDateTime.class),
			eq(PageRequest.of(0, 50))
		)).thenReturn(pendingEvents);

		// Act
		outboxPublisher.publishPending();

		// Assert
		verify(repository, times(1)).findByProcessedOnIsNullAndNextAttemptAtLessThanEqualOrderByOccurredOnAsc(
			any(LocalDateTime.class),
			eq(PageRequest.of(0, 50))
		);
		verify(rabbitTemplate, times(2)).convertAndSend(eq("outbox.events"), anyString(), any(), any(MessagePostProcessor.class));
	}

	@Test
	@DisplayName("Should not publish when no pending events exist")
	void testPublishPending_NoPendingEvents() {
		// Arrange
		when(repository.findByProcessedOnIsNullAndNextAttemptAtLessThanEqualOrderByOccurredOnAsc(
			any(LocalDateTime.class),
			eq(PageRequest.of(0, 50))
		)).thenReturn(Collections.emptyList());

		// Act
		outboxPublisher.publishPending();

		// Assert
		verify(repository, times(1)).findByProcessedOnIsNullAndNextAttemptAtLessThanEqualOrderByOccurredOnAsc(
			any(LocalDateTime.class),
			eq(PageRequest.of(0, 50))
		);
		verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));
	}

	// ===== publishSingle Success Tests =====

	@Test
	@DisplayName("Should successfully publish single event with routing key from event")
	void testPublishSingle_SuccessWithEventRoutingKey() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setRoutingKey("custom.routing.key");
		event.setProcessedOn(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertNotNull(event.getProcessedOn(), "ProcessedOn should be set");
		assertNull(event.getLastError(), "LastError should be null on success");
		verify(rabbitTemplate, times(1)).convertAndSend(
			eq("outbox.events"),
			eq("custom.routing.key"),
			any(),
			any(MessagePostProcessor.class)
		);
		verify(repository, times(1)).save(event);
	}

	@Test
	@DisplayName("Should successfully publish with resolved routing key from event name")
	void testPublishSingle_SuccessWithEventNameRoutingKey() {
		// Arrange
		OutboxEventEntity event = createEvent("appointment.created", "appointment.created", "apt-123");
		event.setRoutingKey(null);
		event.setProcessedOn(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertNotNull(event.getProcessedOn(), "ProcessedOn should be set");
		assertNull(event.getLastError(), "LastError should be null on success");
		verify(rabbitTemplate, times(1)).convertAndSend(
			eq("outbox.events"),
			eq("appointment.created"),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should successfully publish with resolved routing key from event type")
	void testPublishSingle_SuccessWithEventTypeRoutingKey() {
		// Arrange
		OutboxEventEntity event = createEvent(null, "user.registered", "user-123");
		event.setRoutingKey(null);
		event.setEventName(null);
		event.setProcessedOn(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertNotNull(event.getProcessedOn(), "ProcessedOn should be set");
		verify(rabbitTemplate, times(1)).convertAndSend(
			eq("outbox.events"),
			eq("user.registered"),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should successfully publish with default routing key")
	void testPublishSingle_SuccessWithDefaultRoutingKey() {
		// Arrange
		OutboxEventEntity event = createEvent(null, null, "aggregate-1");
		event.setRoutingKey(null);
		event.setEventName("simple");
		event.setProcessedOn(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertNotNull(event.getProcessedOn(), "ProcessedOn should be set");
		verify(rabbitTemplate, times(1)).convertAndSend(
			eq("outbox.events"),
			eq("default.routing.key"),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should set content type to application/json on successful publish")
	void testPublishSingle_SetContentType() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setProcessedOn(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertNotNull(event.getProcessedOn(), "ProcessedOn should be set on success");
		verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));
	}

	// ===== publishSingle Error Handling Tests =====

	@Test
	@DisplayName("Should handle first attempt error with exponential backoff")
	void testPublishSingle_FirstAttemptError() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setAttempts(null);
		event.setLastError(null);

		doThrow(new RuntimeException("Connection failed"))
			.when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertEquals(1, event.getAttempts(), "Attempts should be 1");
		assertEquals("Connection failed", event.getLastError(), "LastError should be set");
		assertNotNull(event.getNextAttemptAt(), "NextAttemptAt should be set");
		assertTrue(event.getNextAttemptAt().isAfter(LocalDateTime.now()), "NextAttemptAt should be in the future");
		assertNull(event.getProcessedOn(), "ProcessedOn should be null on error");
		verify(repository, times(1)).save(event);
	}

	@Test
	@DisplayName("Should handle multiple retry attempts with increasing backoff")
	void testPublishSingle_MultipleRetryAttempts() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setAttempts(2);
		event.setLastError("Previous error");

		doThrow(new RuntimeException("Still failing"))
			.when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertEquals(3, event.getAttempts(), "Attempts should be incremented");
		assertEquals("Still failing", event.getLastError(), "LastError should be updated");
		verify(repository, times(1)).save(event);
	}

	@Test
	@DisplayName("Should cap backoff at publishRetries limit")
	void testPublishSingle_BackoffCappedAtRetries() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setAttempts(10); // More than publishRetries (3)

		doThrow(new RuntimeException("Error")).when(rabbitTemplate)
			.convertAndSend(anyString(), anyString(), any(), any(MessagePostProcessor.class));

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertEquals(11, event.getAttempts(), "Attempts should be incremented");
		// Backoff should be capped at 3 retries
		verify(repository, times(1)).save(event);
	}

	@Test
	@DisplayName("Should handle JSON parsing exception in envelope building")
	void testPublishSingle_InvalidJsonPayload() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setPayload("{invalid json}");
		event.setAttempts(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		assertEquals(1, event.getAttempts(), "Attempts should be incremented");
		assertNotNull(event.getLastError(), "LastError should be set");
		assertTrue(event.getLastError().contains("Failed to parse outbox payload"),
				"Error message should indicate JSON parsing failure");
		assertNull(event.getProcessedOn(), "ProcessedOn should be null on error");
		verify(repository, times(1)).save(event);
	}

	// ===== Envelope Building Tests =====

	@Test
	@DisplayName("Should build correct envelope structure with all fields")
	void testPublishSingle_EnvelopeStructure() {
		// Arrange
		UUID eventId = UUID.randomUUID();
		UUID correlationId = UUID.randomUUID();
		OutboxEventEntity event = createEvent("order.created", "order.created", "order-123");
		event.setEventId(eventId);
		event.setCorrelationId(correlationId);
		event.setSchemaVersion(2);
		event.setPayload("{\"orderId\":\"123\",\"amount\":99.99}");
		event.setOccurredOn(LocalDateTime.of(2026, 4, 21, 10, 30, 0));

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			eq("outbox.events"),
			eq("order.created"),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should generate random eventId when not provided")
	void testPublishSingle_GenerateRandomEventId() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setEventId(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should generate random correlationId when not provided")
	void testPublishSingle_GenerateRandomCorrelationId() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setCorrelationId(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should use default schema version when not provided")
	void testPublishSingle_DefaultSchemaVersion() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setSchemaVersion(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should handle null payload as empty JSON object")
	void testPublishSingle_NullPayload() {
		// Arrange
		OutboxEventEntity event = createEvent("test.event", "test.event", "aggregate-1");
		event.setPayload(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	// ===== Event Name Resolution Tests =====

	@Test
	@DisplayName("Should resolve event name with dot notation from eventName")
	void testPublishSingle_ResolveEventNameWithDot() {
		// Arrange
		OutboxEventEntity event = createEvent("appointment.scheduled", "appointment.scheduled", "apt-1");
		event.setRoutingKey(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should resolve event name with dot notation from eventType")
	void testPublishSingle_ResolveEventNameFromEventType() {
		// Arrange
		OutboxEventEntity event = createEvent(null, "user.created", "user-1");
		event.setEventName("simple");
		event.setRoutingKey(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should resolve event name from eventName when no dot notation")
	void testPublishSingle_ResolveEventNameWithoutDot() {
		// Arrange
		OutboxEventEntity event = createEvent(null, "created", "agg-1");
		event.setEventName("myEvent");
		event.setRoutingKey(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	@Test
	@DisplayName("Should use unknown event name when all sources are empty")
	void testPublishSingle_UnknownEventName() {
		// Arrange
		OutboxEventEntity event = createEvent(null, null, "aggregate-1");
		event.setEventName(null);
		event.setRoutingKey(null);

		// Act
		outboxPublisher.publishSingle(event);

		// Assert
		verify(rabbitTemplate, times(1)).convertAndSend(
			anyString(),
			anyString(),
			any(),
			any(MessagePostProcessor.class)
		);
	}

	// ===== Helper Methods =====

	private OutboxEventEntity createEvent(String eventName, String eventType, String aggregateId) {
		OutboxEventEntity event = new OutboxEventEntity();
		event.setId(UUID.randomUUID());
		event.setEventName(eventName);
		event.setEventType(eventType);
		event.setAggregateId(aggregateId);
		event.setAggregateType("Aggregate");
		event.setPayload("{}");
		event.setOccurredOn(LocalDateTime.now(ZoneOffset.UTC));
		event.setProcessedOn(null);
		event.setAttempts(null);
		event.setLastError(null);
		return event;
	}
}

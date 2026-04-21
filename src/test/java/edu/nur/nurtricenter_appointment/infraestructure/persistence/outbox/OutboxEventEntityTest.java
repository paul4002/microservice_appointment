package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OutboxEventEntityTest {

	private OutboxEventEntity entity;

	@BeforeEach
	void setUp() {
		entity = new OutboxEventEntity();
	}

	@Test
	void setAndGetId_ShouldStoreValue() {
		// Arrange
		UUID id = UUID.randomUUID();

		// Act
		entity.setId(id);

		// Assert
		assertEquals(id, entity.getId());
	}

	@Test
	void setAndGetEventId_ShouldStoreValue() {
		// Arrange
		UUID eventId = UUID.randomUUID();

		// Act
		entity.setEventId(eventId);

		// Assert
		assertEquals(eventId, entity.getEventId());
	}

	@Test
	void setAndGetAggregateType_ShouldStoreValue() {
		// Arrange
		String aggregateType = "Appointment";

		// Act
		entity.setAggregateType(aggregateType);

		// Assert
		assertEquals(aggregateType, entity.getAggregateType());
	}

	@Test
	void setAndGetAggregateId_ShouldStoreValue() {
		// Arrange
		String aggregateId = UUID.randomUUID().toString();

		// Act
		entity.setAggregateId(aggregateId);

		// Assert
		assertEquals(aggregateId, entity.getAggregateId());
	}

	@Test
	void setAndGetEventName_ShouldStoreValue() {
		// Arrange
		String eventName = "citas-evaluaciones.cita.agendada";

		// Act
		entity.setEventName(eventName);

		// Assert
		assertEquals(eventName, entity.getEventName());
	}

	@Test
	void setAndGetPayload_ShouldStoreValue() {
		// Arrange
		String payload = "{\"citaId\":\"123\"}";

		// Act
		entity.setPayload(payload);

		// Assert
		assertEquals(payload, entity.getPayload());
	}

	@Test
	void setAndGetAttempts_ShouldStoreValue() {
		// Arrange
		Integer attempts = 3;

		// Act
		entity.setAttempts(attempts);

		// Assert
		assertEquals(attempts, entity.getAttempts());
	}

	@Test
	void setAndGetOccurredOn_ShouldStoreValue() {
		// Arrange
		LocalDateTime occurredOn = LocalDateTime.of(2026, 4, 20, 10, 0, 0);

		// Act
		entity.setOccurredOn(occurredOn);

		// Assert
		assertEquals(occurredOn, entity.getOccurredOn());
	}

	@Test
	void processedOn_WhenNotSet_ShouldBeNull() {
		// Arrange + Act
		LocalDateTime processedOn = entity.getProcessedOn();

		// Assert
		assertNull(processedOn);
	}

	@Test
	void setAndGetLastError_ShouldStoreValue() {
		// Arrange
		String lastError = "Connection timeout";

		// Act
		entity.setLastError(lastError);

		// Assert
		assertEquals(lastError, entity.getLastError());
	}
}

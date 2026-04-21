package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import edu.nur.nurtricenter_appointment.domain.nutritionists.events.NutritionistDeletedEvent;

public class OutboxEventMapperTest {

	private OutboxEventMapper mapper;
	private NutritionistDeletedEvent event;

	@BeforeEach
	void setUp() {
		ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		mapper = new OutboxEventMapper(objectMapper);
		event = new NutritionistDeletedEvent(UUID.randomUUID());
	}

	@Test
	void toEntity_ShouldReturnNonNullEntity() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertNotNull(entity);
	}

	@Test
	void toEntity_ShouldSetEventId() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(event.getId(), entity.getEventId());
	}

	@Test
	void toEntity_ShouldSetAggregateType() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(event.getAggregateType(), entity.getAggregateType());
	}

	@Test
	void toEntity_ShouldSetAggregateId() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(event.getAggregateId(), entity.getAggregateId());
	}

	@Test
	void toEntity_ShouldSetEventName() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(event.getEventName(), entity.getEventName());
	}

	@Test
	void toEntity_ShouldSetRoutingKeyEqualToEventName() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(event.getEventName(), entity.getRoutingKey());
	}

	@Test
	void toEntity_ShouldSetSchemaVersionTo1() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(1, entity.getSchemaVersion());
	}

	@Test
	void toEntity_ShouldSetOccurredOn() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(event.getOccurredOn(), entity.getOccurredOn());
	}

	@Test
	void toEntity_ShouldSetAttemptsToZero() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertEquals(0, entity.getAttempts());
	}

	@Test
	void toEntity_ShouldSetProcessedOnToNull() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertNull(entity.getProcessedOn());
	}

	@Test
	void toEntity_ShouldSetNonNullPayload() {
		// Arrange + Act
		OutboxEventEntity entity = mapper.toEntity(event);

		// Assert
		assertNotNull(entity.getPayload());
	}
}

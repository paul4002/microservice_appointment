package edu.nur.nurtricenter_appointment.domain.nutritionists.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NutritionistDeletedEventTest {

	private UUID nutricionistaId;
	private NutritionistDeletedEvent event;

	@BeforeEach
	void setUp() {
		nutricionistaId = UUID.randomUUID();
		event = new NutritionistDeletedEvent(nutricionistaId);
	}

	@Test
	void getNutricionistaId_ShouldReturnNutricionistaId() {
		// Arrange
		UUID expectedId = nutricionistaId;

		// Act
		UUID result = event.getNutricionistaId();

		// Assert
		assertEquals(expectedId, result);
	}

	@Test
	void getAggregateId_ShouldReturnNutricionistaIdAsString() {
		// Arrange
		String expectedAggregateId = nutricionistaId.toString();

		// Act
		String result = event.getAggregateId();

		// Assert
		assertEquals(expectedAggregateId, result);
	}

	@Test
	void getAggregateType_ShouldReturnNutritionist() {
		// Arrange
		String expectedType = "Nutritionist";

		// Act
		String result = event.getAggregateType();

		// Assert
		assertEquals(expectedType, result);
	}

	@Test
	void getEventName_ShouldReturnEventName() {
		// Arrange
		String expectedEventName = "citas-evaluaciones.nutricionista.deshabilitado";

		// Act
		String result = event.getEventName();

		// Assert
		assertEquals(expectedEventName, result);
	}

	@Test
	void getPayload_ShouldReturnNonNullPayload() {
		// Arrange + Act
		Object result = event.getPayload();

		// Assert
		assertNotNull(result);
	}

	@Test
	void getPayload_ShouldContainNutricionistaId() {
		// Arrange
		String expectedId = nutricionistaId.toString();

		// Act
		Object payload = event.getPayload();

		// Assert
		assertNotNull(payload);
		assertEquals(true, payload.toString().contains(expectedId));
	}

	@Test
	void getId_ShouldReturnNonNull() {
		// Arrange + Act
		UUID result = event.getId();

		// Assert
		assertNotNull(result);
	}

	@Test
	void getOccurredOn_ShouldReturnNonNull() {
		// Arrange + Act
		LocalDateTime result = event.getOccurredOn();

		// Assert
		assertNotNull(result);
	}

	@Test
	void getEventType_ShouldReturnSimpleClassName() {
		// Arrange
		String expectedType = "NutritionistDeletedEvent";

		// Act
		String result = event.getEventType();

		// Assert
		assertEquals(expectedType, result);
	}
}

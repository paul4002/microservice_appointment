package edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

class PercentageConverterTest {
	private final PercentageConverter converter = new PercentageConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		Percentage percentage = new Percentage(new DecimalValue(18.5));
		// Act
		Double result = converter.convertToDatabaseColumn(percentage);
		// Assert
		assertEquals(18.5, result);
	}

	@Test
	void shouldReturnNullWhenConvertToDatabaseColumnWithNull() {
		// Act
		Double result = converter.convertToDatabaseColumn(null);
		// Assert
		assertNull(result);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		// Arrange
		Double dbValue = 22.0;
		// Act
		Percentage result = converter.convertToEntityAttribute(dbValue);
		// Assert
		assertNotNull(result);
		assertEquals(22.0, result.value().value());
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		Percentage result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

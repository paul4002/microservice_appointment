package edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;

class QuantityValueConverterTest {
	private final QuantityValueConverter converter = new QuantityValueConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		QuantityValue quantity = new QuantityValue(5);
		// Act
		Integer result = converter.convertToDatabaseColumn(quantity);
		// Assert
		assertEquals(5, result);
	}

	@Test
	void shouldReturnNullWhenConvertToDatabaseColumnWithNull() {
		// Act
		Integer result = converter.convertToDatabaseColumn(null);
		// Assert
		assertNull(result);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		// Arrange
		Integer dbValue = 10;
		// Act
		QuantityValue result = converter.convertToEntityAttribute(dbValue);
		// Assert
		assertNotNull(result);
		assertEquals(10, result.value());
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		QuantityValue result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.patients.Cellphone;

public class CellphoneConverterTest {
	private final CellphoneConverter converter = new CellphoneConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		Cellphone cellphone = new Cellphone("71234567");
		// Act
		String result = converter.convertToDatabaseColumn(cellphone);
		// Assert
		assertEquals("71234567", result);
	}

	@Test
	void shouldReturnNullWhenConvertToDatabaseColumnWithNull() {
		// Act
		String result = converter.convertToDatabaseColumn(null);
		// Assert
		assertNull(result);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		// Arrange
		String dbValue = "71234567";
		// Act
		Cellphone result = converter.convertToEntityAttribute(dbValue);
		// Assert
		assertNotNull(result);
		assertEquals("71234567", result.value());
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		Cellphone result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

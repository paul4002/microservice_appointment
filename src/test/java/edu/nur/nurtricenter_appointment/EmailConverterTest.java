package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.patients.Email;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.EmailConverter;

public class EmailConverterTest {
	private final EmailConverter converter = new EmailConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		Email email = new Email("user@example.com");
		// Act
		String result = converter.convertToDatabaseColumn(email);
		// Assert
		assertEquals("user@example.com", result);
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
		String dbValue = "admin@test.com";
		// Act
		Email result = converter.convertToEntityAttribute(dbValue);
		// Assert
		assertNotNull(result);
		assertEquals("admin@test.com", result.value());
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		Email result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

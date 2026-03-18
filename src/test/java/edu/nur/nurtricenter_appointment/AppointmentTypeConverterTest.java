package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.AppointmentTypeConverter;

public class AppointmentTypeConverterTest {
	private final AppointmentTypeConverter converter = new AppointmentTypeConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		AppointmentType type = AppointmentType.INITIAL;
		// Act
		String result = converter.convertToDatabaseColumn(type);
		// Assert
		assertEquals(type.getLabel(), result);
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
		String label = AppointmentType.FOLLOWUP.getLabel();
		// Act
		AppointmentType result = converter.convertToEntityAttribute(label);
		// Assert
		assertNotNull(result);
		assertEquals(AppointmentType.FOLLOWUP, result);
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		AppointmentType result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

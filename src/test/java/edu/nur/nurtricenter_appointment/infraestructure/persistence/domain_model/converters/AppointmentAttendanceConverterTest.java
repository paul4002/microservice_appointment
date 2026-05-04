package edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;

public class AppointmentAttendanceConverterTest {
	private final AppointmentAttendanceConverter converter = new AppointmentAttendanceConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		AppointmentAttendance attendance = AppointmentAttendance.ATTENDED;
		// Act
		String result = converter.convertToDatabaseColumn(attendance);
		// Assert
		assertEquals(attendance.getLabel(), result);
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
		AppointmentAttendance expected = AppointmentAttendance.PENDING;
		// Act
		AppointmentAttendance result = converter.convertToEntityAttribute(expected.getLabel());
		// Assert
		assertEquals(expected, result);
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		AppointmentAttendance result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

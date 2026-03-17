package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.AppointmentStatusConverter;

public class AppointmentStatusConverterTest {
	private final AppointmentStatusConverter converter = new AppointmentStatusConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		AppointmentStatus status = AppointmentStatus.SCHEDULED;
		// Act
		String result = converter.convertToDatabaseColumn(status);
		// Assert
		assertEquals(status.getLabel(), result);
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
		String label = AppointmentStatus.CANCELLED.getLabel();
		// Act
		AppointmentStatus result = converter.convertToEntityAttribute(label);
		// Assert
		assertNotNull(result);
		assertEquals(AppointmentStatus.CANCELLED, result);
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		AppointmentStatus result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

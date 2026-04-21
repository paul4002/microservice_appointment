package edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ScheduleAppointmentDtoTest {

	@Test
	void constructor_WithAllFields_ShouldStoreValues() {
		// Arrange
		String patientId = "11111111-1111-1111-1111-111111111111";
		String nutritionistId = "22222222-2222-2222-2222-222222222222";
		String type = "INITIAL";
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 10, 9, 0, 0);

		// Act
		ScheduleAppointmentDto dto = new ScheduleAppointmentDto(patientId, nutritionistId, type, scheduleDate);

		// Assert
		assertEquals(patientId, dto.patientId);
		assertEquals(nutritionistId, dto.nutritionistId);
		assertEquals(type, dto.type);
		assertEquals(scheduleDate, dto.scheduleDate);
	}

	@Test
	void defaultConstructor_ShouldCreateDtoWithNullFields() {
		// Arrange + Act
		ScheduleAppointmentDto dto = new ScheduleAppointmentDto();

		// Assert
		assertNotNull(dto);
		assertNull(dto.patientId);
		assertNull(dto.nutritionistId);
	}
}

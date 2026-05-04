package edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ScheduledAppointmentNutritionistDtoTest {

	@Test
	void fields_ShouldBeAssignableAndReadable() {
		// Arrange
		String id = "aaaa-bbbb-cccc";
		String patientId = "dddd-eeee-ffff";
		String type = "INITIAL";
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 15, 10, 0, 0);
		String status = "SCHEDULED";
		String attendance = "PENDING";

		// Act
		ScheduledAppointmentNutritionistDto dto = new ScheduledAppointmentNutritionistDto();
		dto.id = id;
		dto.patientId = patientId;
		dto.type = type;
		dto.scheduleDate = scheduleDate;
		dto.status = status;
		dto.attendance = attendance;

		// Assert
		assertEquals(id, dto.id);
		assertEquals(patientId, dto.patientId);
		assertEquals(type, dto.type);
		assertEquals(scheduleDate, dto.scheduleDate);
		assertEquals(status, dto.status);
		assertEquals(attendance, dto.attendance);
	}

	@Test
	void defaultConstructor_ShouldCreateDtoWithNullFields() {
		// Arrange + Act
		ScheduledAppointmentNutritionistDto dto = new ScheduledAppointmentNutritionistDto();

		// Assert
		assertNotNull(dto);
		assertNull(dto.id);
		assertNull(dto.patientId);
	}
}

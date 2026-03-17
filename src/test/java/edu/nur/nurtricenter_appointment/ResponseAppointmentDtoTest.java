package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment.ResponseAppointmentDto;

public class ResponseAppointmentDtoTest {
	@Test
	void shouldCreateEmptyDtoWithDefaultConstructor() {
		ResponseAppointmentDto dto = new ResponseAppointmentDto();

		assertNull(dto.id);
		assertNull(dto.patientId);
		assertNull(dto.nutritionistId);
		assertNull(dto.type);
		assertNull(dto.creationDate);
		assertNull(dto.scheduleDate);
		assertNull(dto.cancelDate);
		assertNull(dto.status);
		assertNull(dto.attendance);
	}

	@Test
	void shouldCreateDtoWithIdConstructor() {
		String id = UUID.randomUUID().toString();
		ResponseAppointmentDto dto = new ResponseAppointmentDto(id);

		assertEquals(id, dto.id);
		assertNull(dto.patientId);
		assertNull(dto.nutritionistId);
		assertNull(dto.type);
	}

	@Test
	void shouldCreateDtoWithPartialConstructor() {
		String patientId = UUID.randomUUID().toString();
		String nutritionistId = UUID.randomUUID().toString();
		String type = "Initial";
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 1, 19, 10, 30);

		ResponseAppointmentDto dto = new ResponseAppointmentDto(patientId, nutritionistId, type, scheduleDate);

		assertEquals(patientId, dto.patientId);
		assertEquals(nutritionistId, dto.nutritionistId);
		assertEquals(type, dto.type);
		assertEquals(scheduleDate, dto.scheduleDate);

		assertNull(dto.id);
		assertNull(dto.creationDate);
		assertNull(dto.cancelDate);
		assertNull(dto.status);
		assertNull(dto.attendance);
	}

	@Test
	void shouldCreateDtoWithFullConstructor() {
		String id = UUID.randomUUID().toString();
		String patientId = UUID.randomUUID().toString();
		String nutritionistId = UUID.randomUUID().toString();
		String type = "Initial";
		LocalDateTime creationDate = LocalDateTime.of(2026, 1, 18, 9, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 1, 19, 10, 30);
		LocalDateTime cancelDate = LocalDateTime.of(2026, 1, 19, 11, 0);
		String status = "Scheduled";
		String attendance = "Pending";

		ResponseAppointmentDto dto = new ResponseAppointmentDto(id, patientId, nutritionistId, type,
						creationDate, scheduleDate, cancelDate, status, attendance);

		assertEquals(id, dto.id);
		assertEquals(patientId, dto.patientId);
		assertEquals(nutritionistId, dto.nutritionistId);
		assertEquals(type, dto.type);
		assertEquals(creationDate, dto.creationDate);
		assertEquals(scheduleDate, dto.scheduleDate);
		assertEquals(cancelDate, dto.cancelDate);
		assertEquals(status, dto.status);
		assertEquals(attendance, dto.attendance);
	}
}

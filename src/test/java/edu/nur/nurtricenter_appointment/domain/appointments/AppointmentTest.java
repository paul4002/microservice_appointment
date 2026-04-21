package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;

public class AppointmentTest {

	@Test
	void schedule_ShouldCreateScheduledAppointment() {
			// Arrange
			UUID patientId = UUID.randomUUID();
			UUID nutritionistId = UUID.randomUUID();
			LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

			// Act
			Appointment appointment = Appointment.schedule(patientId, nutritionistId, futureDate, AppointmentType.INITIAL);

			// Assert
			assertEquals(patientId, appointment.getPatientId());
			assertEquals(nutritionistId, appointment.getNutritionistId());
			assertEquals(AppointmentType.INITIAL, appointment.getType());
			assertEquals(futureDate, appointment.getScheduledDate());
			assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());
			assertEquals(AppointmentAttendance.PENDING, appointment.getAttendance());
			assertNotNull(appointment.getCreationDate());
	}

	@Test
	void cancel_ShouldSetStatusCancelledAndRegisterCancelDate() {
			// Arrange
			Appointment appointment = Appointment.schedule(
							UUID.randomUUID(),
							UUID.randomUUID(),
							LocalDateTime.now().plusDays(1),
							AppointmentType.FOLLOWUP
			);

			// Act
			appointment.cancel();

			// Assert
			assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
			assertEquals(AppointmentAttendance.NOT_ATTENDED, appointment.getAttendance());
			assertNotNull(appointment.getCancelDate());
	}

	@Test
	void attend_ShouldSetStatusCompletedAndFillFields() {
			// Arrange
			Appointment appointment = Appointment.schedule(
							UUID.randomUUID(),
							UUID.randomUUID(),
							LocalDateTime.now().plusDays(1),
							AppointmentType.FOLLOWUP
			);

			String notes = "Paciente estable";
			Measurement measurement = new Measurement();
			Diagnosis diagnosis = new Diagnosis();

			// Act
			appointment.attend(notes, measurement, diagnosis);

			// Assert
			assertEquals(AppointmentStatus.COMPLETED, appointment.getStatus());
			assertEquals(AppointmentAttendance.ATTENDED, appointment.getAttendance());
			assertEquals(notes, appointment.getNotes());
			assertEquals(measurement, appointment.getMeasurement());
			assertEquals(diagnosis, appointment.getDiagnosis());
	}

	@Test
	void notAttended_ShouldSetStatusClosed() {
		// Arrange
		Appointment appointment = Appointment.schedule(
				UUID.randomUUID(),
				UUID.randomUUID(),
				LocalDateTime.now().plusDays(1),
				AppointmentType.FOLLOWUP
		);

		// Act
		appointment.notAttended();

		// Assert
		assertEquals(AppointmentStatus.CLOSED, appointment.getStatus());
		assertEquals(AppointmentAttendance.NOT_ATTENDED, appointment.getAttendance());
	}

	@Test
	void shouldCreateAppointmentWithAllFields() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		AppointmentType type = AppointmentType.INITIAL;
		AppointmentStatus status = AppointmentStatus.SCHEDULED;
		AppointmentAttendance attendance = AppointmentAttendance.PENDING;
		LocalDateTime creationDate = LocalDateTime.now();
		LocalDateTime scheduleDate = creationDate.plusDays(3);
		LocalDateTime cancelDate = null;
		String notes = "Primera consulta nutricional";

		// Act
		Appointment appointment = new Appointment(
						appointmentId,
						patientId,
						nutritionistId,
						type,
						creationDate,
						scheduleDate,
						cancelDate,
						status,
						attendance,
						notes
		);

		// Assert
		assertEquals(appointmentId, appointment.getId());
		assertEquals(patientId, appointment.getPatientId());
		assertEquals(nutritionistId, appointment.getNutritionistId());
		assertEquals(type, appointment.getType());
		assertEquals(status, appointment.getStatus());
		assertEquals(attendance, appointment.getAttendance());
		assertEquals(creationDate, appointment.getCreationDate());
		assertEquals(scheduleDate, appointment.getScheduledDate());
		assertNull(appointment.getCancelDate());
		assertEquals(notes, appointment.getNotes());
	}
}

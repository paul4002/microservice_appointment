package edu.nur.nurtricenter_appointment.domain.appointments.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppointmentScheduledEventTest {

	private UUID appointmentId;
	private UUID patientId;
	private LocalDateTime scheduledDate;
	private AppointmentScheduledEvent event;

	@BeforeEach
	void setUp() {
		appointmentId = UUID.randomUUID();
		patientId = UUID.randomUUID();
		scheduledDate = LocalDateTime.of(2026, 4, 25, 10, 30, 0);
		event = new AppointmentScheduledEvent(appointmentId, patientId, scheduledDate);
	}

	@Test
	void getAppointmentId_ShouldReturnAppointmentId() {
		// Arrange
		UUID expectedId = appointmentId;

		// Act
		UUID result = event.getAppointmentId();

		// Assert
		assertEquals(expectedId, result);
	}

	@Test
	void getPatientId_ShouldReturnPatientId() {
		// Arrange
		UUID expectedPatientId = patientId;

		// Act
		UUID result = event.getPatientId();

		// Assert
		assertEquals(expectedPatientId, result);
	}

	@Test
	void getScheduledDate_ShouldReturnScheduledDate() {
		// Arrange
		LocalDateTime expectedDate = scheduledDate;

		// Act
		LocalDateTime result = event.getScheduledDate();

		// Assert
		assertEquals(expectedDate, result);
	}

	@Test
	void getAggregateId_ShouldReturnAppointmentIdAsString() {
		// Arrange
		String expectedAggregateId = appointmentId.toString();

		// Act
		String result = event.getAggregateId();

		// Assert
		assertEquals(expectedAggregateId, result);
	}

	@Test
	void getAggregateType_ShouldReturnAppointment() {
		// Arrange
		String expectedType = "Appointment";

		// Act
		String result = event.getAggregateType();

		// Assert
		assertEquals(expectedType, result);
	}

	@Test
	void getEventName_ShouldReturnEventName() {
		// Arrange
		String expectedEventName = "citas-evaluaciones.cita.agendada";

		// Act
		String result = event.getEventName();

		// Assert
		assertEquals(expectedEventName, result);
	}

	@Test
	void getPayload_ShouldReturnNonNullPayload() {
		// Arrange + Act
		Object result = event.getPayload();

		// Assert
		assertNotNull(result);
	}

	@Test
	void getPayload_ShouldContainFormattedScheduledDate() {
		// Arrange
		String expectedFormattedDate = scheduledDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

		// Act
		Object payload = event.getPayload();
		String payloadString = payload.toString();

		// Assert
		assertNotNull(payload);
		assertEquals(true, payloadString.contains(expectedFormattedDate));
	}

	@Test
	void getPayload_ShouldContainAppointmentIdAndPatientId() {
		// Arrange
		String expectedCitaId = appointmentId.toString();
		String expectedPacienteId = patientId.toString();

		// Act
		Object payload = event.getPayload();
		String payloadString = payload.toString();

		// Assert
		assertNotNull(payload);
		assertEquals(true, payloadString.contains(expectedCitaId));
		assertEquals(true, payloadString.contains(expectedPacienteId));
	}
}

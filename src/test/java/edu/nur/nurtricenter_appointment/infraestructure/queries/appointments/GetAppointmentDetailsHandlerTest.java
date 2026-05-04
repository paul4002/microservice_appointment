package edu.nur.nurtricenter_appointment.infraestructure.queries.appointments;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.AppointmentDetailsDto;
import edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details.GetAppointmentDetailsQuery;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

@ExtendWith(MockitoExtension.class)
class GetAppointmentDetailsHandlerTest {

	@Mock
	private IAppointmentRepository appointmentRepository;

	private GetAppointmentDetailsHandler handler;

	@BeforeEach
	void setUp() {
		handler = new GetAppointmentDetailsHandler(appointmentRepository);
	}

	@Test
	void testHandleAppointmentNotFound() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(null);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertFalse(result.isSuccess());
		assertTrue(result.isFailure());
		assertNull(result.getValue());
		assertEquals("Appointment.NotFound", result.getError().getCode());
		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentFoundWithoutMeasurementAndDiagnosis() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime creationDate = LocalDateTime.of(2026, 5, 1, 10, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 5, 14, 30);

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.INITIAL, creationDate, scheduleDate, null,
				AppointmentStatus.SCHEDULED, AppointmentAttendance.PENDING, "Test notes");

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();
		assertEquals(appointmentId.toString(), dto.id);
		assertEquals(patientId.toString(), dto.patientId);
		assertEquals(nutritionistId.toString(), dto.nutritionistId);
		assertEquals("Initial", dto.type);
		assertEquals("Scheduled", dto.status);
		assertEquals("PENDING", dto.attendance);
		assertNull(dto.measurement);
		assertNull(dto.diagnosis);
		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentFoundWithMeasurement() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime creationDate = LocalDateTime.of(2026, 5, 1, 10, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 5, 14, 30);

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.INITIAL, creationDate, scheduleDate, null,
				AppointmentStatus.SCHEDULED, AppointmentAttendance.PENDING, "Test notes");

		Measurement measurement = new Measurement(
			new DecimalValue(75.5),
			new DecimalValue(180),
			new DecimalValue(23.3),
			new Percentage(new DecimalValue(18.5)),
			new Percentage(new DecimalValue(35.2))
		);
		appointment.setMeasurement(measurement);

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();
		assertNotNull(dto.measurement);
		assertNotNull(dto.measurement.weight);
		assertEquals("75.5", dto.measurement.weight.value);
		assertEquals("KG", dto.measurement.weight.unit);
		assertNotNull(dto.measurement.height);
		assertEquals("180.0", dto.measurement.height.value);
		assertEquals("CM", dto.measurement.height.unit);
		assertEquals("23.3", dto.measurement.imc);
		assertEquals("18.5", dto.measurement.bodyFat);
		assertEquals("35.2", dto.measurement.muscleMass);
		assertNull(dto.diagnosis);
		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentFoundWithDiagnosis() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime creationDate = LocalDateTime.of(2026, 5, 1, 10, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 5, 14, 30);

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.INITIAL, creationDate, scheduleDate, null,
				AppointmentStatus.SCHEDULED, AppointmentAttendance.PENDING, "Test notes");

		Diagnosis diagnosis = new Diagnosis(
			"Complete nutritional assessment",
			DiagnosisNutritionalState.NORMAL_WEIGHT,
			"Low risk",
			"Balanced diet with high protein",
			"Maintain current weight",
			"Good adherence to plan"
		);
		appointment.setDiagnosis(diagnosis);

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();
		assertNull(dto.measurement);
		assertNotNull(dto.diagnosis);
		assertEquals("Complete nutritional assessment", dto.diagnosis.description);
		assertEquals("NORMAL_WEIGHT", dto.diagnosis.nutritionalState);
		assertEquals("Low risk", dto.diagnosis.associatedRisks);
		assertEquals("Balanced diet with high protein", dto.diagnosis.recommendations);
		assertEquals("Maintain current weight", dto.diagnosis.goals);
		assertEquals("Good adherence to plan", dto.diagnosis.comments);
		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentFoundWithCompleteData() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime creationDate = LocalDateTime.of(2026, 5, 1, 10, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 5, 14, 30);
		LocalDateTime cancelDate = null;

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.FOLLOWUP, creationDate, scheduleDate, cancelDate,
				AppointmentStatus.COMPLETED, AppointmentAttendance.ATTENDED, "Attended successfully");

		Measurement measurement = new Measurement(
			new DecimalValue(72.0),
			new DecimalValue(175),
			new DecimalValue(23.5),
			new Percentage(new DecimalValue(20.0)),
			new Percentage(new DecimalValue(36.0))
		);
		appointment.setMeasurement(measurement);

		Diagnosis diagnosis = new Diagnosis(
			"Follow-up assessment",
			DiagnosisNutritionalState.NORMAL_WEIGHT,
			"No risks",
			"Continue current plan",
			"Maintain weight loss",
			"Excellent progress"
		);
		appointment.setDiagnosis(diagnosis);

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();
		assertEquals(appointmentId.toString(), dto.id);
		assertEquals(patientId.toString(), dto.patientId);
		assertEquals(nutritionistId.toString(), dto.nutritionistId);
		assertEquals("Follow up", dto.type);
		assertEquals(creationDate, dto.creationDate);
		assertEquals(scheduleDate, dto.scheduleDate);
		assertNull(dto.cancelDate);
		assertEquals("Completed", dto.status);
		assertEquals("ATTENDED", dto.attendance);

		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentWithCompleteDataMeasurementAndDiagnosis() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.FOLLOWUP, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null,
				AppointmentStatus.COMPLETED, AppointmentAttendance.ATTENDED, "Attended");

		Measurement measurement = new Measurement(
			new DecimalValue(72.0),
			new DecimalValue(175),
			new DecimalValue(23.5),
			new Percentage(new DecimalValue(20.0)),
			new Percentage(new DecimalValue(36.0))
		);
		appointment.setMeasurement(measurement);

		Diagnosis diagnosis = new Diagnosis(
			"Follow-up assessment",
			DiagnosisNutritionalState.NORMAL_WEIGHT,
			"No risks",
			"Continue current plan",
			"Maintain weight loss",
			"Excellent progress"
		);
		appointment.setDiagnosis(diagnosis);

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();

		assertNotNull(dto.measurement);
		assertEquals("72.0", dto.measurement.weight.value);
		assertEquals("KG", dto.measurement.weight.unit);
		assertEquals("175.0", dto.measurement.height.value);
		assertEquals("CM", dto.measurement.height.unit);
		assertEquals("23.5", dto.measurement.imc);
		assertEquals("20.0", dto.measurement.bodyFat);
		assertEquals("36.0", dto.measurement.muscleMass);

		assertNotNull(dto.diagnosis);
		assertEquals("Follow-up assessment", dto.diagnosis.description);
		assertEquals("NORMAL_WEIGHT", dto.diagnosis.nutritionalState);
		assertEquals("No risks", dto.diagnosis.associatedRisks);
		assertEquals("Continue current plan", dto.diagnosis.recommendations);
		assertEquals("Maintain weight loss", dto.diagnosis.goals);
		assertEquals("Excellent progress", dto.diagnosis.comments);

		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentFoundWithCancelledStatus() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime creationDate = LocalDateTime.of(2026, 5, 1, 10, 0);
		LocalDateTime scheduleDate = LocalDateTime.of(2026, 5, 5, 14, 30);
		LocalDateTime cancelDate = LocalDateTime.of(2026, 5, 3, 9, 0);

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.INITIAL, creationDate, scheduleDate, cancelDate,
				AppointmentStatus.CANCELLED, AppointmentAttendance.NOT_ATTENDED, "Cancelled by patient");

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();
		assertEquals("Cancelled", dto.status);
		assertEquals("NOT_ATTENDED", dto.attendance);
		assertEquals(cancelDate, dto.cancelDate);
		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}

	@Test
	void testHandleAppointmentWithPartialMeasurementData() {
		// Arrange
		UUID appointmentId = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();

		Appointment appointment = new Appointment(appointmentId, patientId, nutritionistId,
				AppointmentType.INITIAL, LocalDateTime.now(), LocalDateTime.now().plusDays(7), null,
				AppointmentStatus.SCHEDULED, AppointmentAttendance.PENDING, "Test");

		Measurement measurement = new Measurement(
			new DecimalValue(70.0),
			new DecimalValue(170),
			null,
			null,
			null
		);
		appointment.setMeasurement(measurement);

		GetAppointmentDetailsQuery query = new GetAppointmentDetailsQuery(appointmentId);
		when(appointmentRepository.GetById(appointmentId)).thenReturn(appointment);

		// Act
		ResultWithValue<AppointmentDetailsDto> result = handler.handle(query);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		AppointmentDetailsDto dto = result.getValue();
		assertNotNull(dto.measurement);
		assertEquals("70.0", dto.measurement.weight.value);
		assertEquals("170.0", dto.measurement.height.value);
		assertNull(dto.measurement.imc);
		assertNull(dto.measurement.bodyFat);
		assertNull(dto.measurement.muscleMass);
		verify(appointmentRepository, times(1)).GetById(appointmentId);
	}
}

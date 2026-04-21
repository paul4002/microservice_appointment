package edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment.ScheduleAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment.ScheduleAppointmentHandler;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.patients.IPatientRepository;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScheduleAppointmentHandlerTest {
	@Mock IAppointmentRepository appointmentRepository;
	@Mock IPatientRepository patientRepository;
	@Mock INutritionistRepository nutritionistRepository;
	@Mock IUnitOfWork unitOfWork;

	@InjectMocks
	ScheduleAppointmentHandler handler;

	@Test
	void handle_ValidRequest_ShouldReturnSuccessWithAppointmentId() {
		// Arrange
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		LocalDateTime scheduleDate = LocalDateTime.now().plusDays(2);
		String type = AppointmentType.INITIAL.getLabel();

		Patient patient = mock(Patient.class);
		Nutritionist nutritionist = mock(Nutritionist.class);

		ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
				patientId,
				nutritionistId,
				type,
				scheduleDate
		);

		when(patientRepository.GetById(any(UUID.class))).thenReturn(patient);
		when(nutritionistRepository.GetById(any(UUID.class))).thenReturn(nutritionist);
		when(appointmentRepository.existsAppointmentNearTime(any(), any(), any())).thenReturn(false);

		// Act
		ResultWithValue<UUID> result = handler.handle(command);

		// Assert
		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		verify(appointmentRepository, times(1)).add(any());
		verify(unitOfWork, times(1)).commitAsync();
	}

	@Test
	void handle_WhenPatientNotFound_ShouldReturnFailure() {
		// Arrange
		UUID patientId = UUID.randomUUID();
		ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
			patientId,
			UUID.randomUUID(),
			AppointmentType.INITIAL.getLabel(),
			LocalDateTime.now().plusDays(1)
		);

		when(patientRepository.GetById(patientId)).thenReturn(null);

		// Act
		ResultWithValue<UUID> result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("Patient.NotFound", result.getError().getCode());
		verify(appointmentRepository, never()).add(any());
	}

	@Test
	void handle_WhenNutritionistNotFound_ShouldReturnFailure() {
		// Arrange
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		Patient patient = mock(Patient.class);
		ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
			patientId,
			nutritionistId,
			AppointmentType.INITIAL.getLabel(),
			LocalDateTime.now().plusDays(1)
		);

		when(patientRepository.GetById(patientId)).thenReturn(patient);
		when(nutritionistRepository.GetById(nutritionistId)).thenReturn(null);

		// Act
		var result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("Nutritionist.NotFound", result.getError().getCode());
	}

	@Test
	void handle_WhenDateIsInPast_ShouldReturnFailure() {
		// Arrange
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		// Fecha ayer
		LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
		ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
			patientId,
			nutritionistId,
			AppointmentType.INITIAL.getLabel(),
			pastDate
		);

		Patient patient = mock(Patient.class);
		Nutritionist nutritionist = mock(Nutritionist.class);

		when(patientRepository.GetById(patientId)).thenReturn(patient);
		when(nutritionistRepository.GetById(nutritionistId)).thenReturn(nutritionist);

		// Act
		var result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("Appointment.InvalidScheduleDate", result.getError().getCode());
	}
}

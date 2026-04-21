package edu.nur.nurtricenter_appointment.webapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import an.awesome.pipelinr.Pipeline;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.AttendAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.DiagnosisDto;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MeasurementDto;
import edu.nur.nurtricenter_appointment.application.appointments.cancelAppointment.CancelAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.notAttendedAppointment.NotAttendedAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment.ScheduleAppointmentCommand;
import edu.nur.nurtricenter_appointment.core.results.Result;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

	@Mock
	Pipeline pipeline;

	@InjectMocks
	AppointmentController controller;

	@Test
	void scheduleAppointment_ShouldDelegateToPipelineAndReturnResult() {
		// Arrange
		UUID expectedId = UUID.randomUUID();
		ResultWithValue<UUID> expected = Result.success(expectedId);
		ScheduleAppointmentCommand command = new ScheduleAppointmentCommand(
				UUID.randomUUID(), UUID.randomUUID(),
				AppointmentType.INITIAL.getLabel(),
				LocalDateTime.now().plusDays(1));
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<UUID> result = controller.scheduleAppointment(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}

	@Test
	void cancelAppointment_ShouldDelegateToPipelineAndReturnResult() {
		// Arrange
		ResultWithValue<Boolean> expected = Result.success(Boolean.TRUE);
		CancelAppointmentCommand command = new CancelAppointmentCommand(UUID.randomUUID());
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<Boolean> result = controller.cancelAppointment(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}

	@Test
	void notAttendedAppointment_ShouldDelegateToPipelineAndReturnResult() {
		// Arrange
		ResultWithValue<Boolean> expected = Result.success(Boolean.TRUE);
		NotAttendedAppointmentCommand command = new NotAttendedAppointmentCommand(UUID.randomUUID());
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<Boolean> result = controller.notAttendedAppointment(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}

	@Test
	void attend_ShouldDelegateToPipelineAndReturnResult() {
		// Arrange
		ResultWithValue<Boolean> expected = Result.success(Boolean.TRUE);
		AttendAppointmentCommand command = new AttendAppointmentCommand(
				UUID.randomUUID(), "notes", new MeasurementDto(), new DiagnosisDto());
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<Boolean> result = controller.attend(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}
}

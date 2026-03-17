package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.nur.nurtricenter_appointment.application.appointments.notAttendedAppointment.NotAttendedAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.notAttendedAppointment.NotAttendedAppointmentHandler;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;

public class NotAttendedAppointmentHandlerTest {
	@Mock
	private IAppointmentRepository appointmentRepository;

	@Mock
	private IUnitOfWork unitOfWork;

	private NotAttendedAppointmentHandler handler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		handler = new NotAttendedAppointmentHandler(appointmentRepository, unitOfWork);
	}

	@Test
	void shouldReturnFailureWhenAppointmentNotFound() {
		UUID id = UUID.randomUUID();
		NotAttendedAppointmentCommand cmd = new NotAttendedAppointmentCommand(id);

		when(appointmentRepository.GetById(id)).thenReturn(null);

		var result = handler.handle(cmd);

		assertFalse(result.isSuccess());
		assertEquals("Appointment.NotFound", result.getError().getCode());
	}

	@Test
	void shouldMarkAppointmentAsNotAttendedSuccessfully() throws DomainException {
		UUID id = UUID.randomUUID();
		Appointment appointment = mock(Appointment.class);
		NotAttendedAppointmentCommand cmd = new NotAttendedAppointmentCommand(id);

		when(appointmentRepository.GetById(id)).thenReturn(appointment);

		var result = handler.handle(cmd);

		verify(appointment).notAttended();

		verify(appointmentRepository).update(appointment);
		verify(unitOfWork).commitAsync();

		assertTrue(result.isSuccess());
		assertEquals(true, result.getValue());
	}

	@Test
	void shouldReturnFailureWhenNotAttendedThrowsDomainException() throws DomainException {
		UUID id = UUID.randomUUID();
		Appointment appointment = mock(Appointment.class);
		NotAttendedAppointmentCommand cmd = new NotAttendedAppointmentCommand(id);

		when(appointmentRepository.GetById(id)).thenReturn(appointment);
		DomainException ex = new DomainException(Error.failure("Test.Code", "Test message"));
		doThrow(ex).when(appointment).notAttended();

		var result = handler.handle(cmd);

		assertFalse(result.isSuccess());
		assertEquals("Test.Code", result.getError().getCode());

		verify(appointmentRepository, never()).update(any());
		verify(unitOfWork, never()).commitAsync();
	}
}

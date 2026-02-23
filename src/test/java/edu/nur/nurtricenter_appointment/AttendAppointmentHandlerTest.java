package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.AttendAppointmentCommand;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.AttendAppointmentHandler;
import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.infraestructure.config.UnitOfWorkJpa;

public class AttendAppointmentHandlerTest {
  @Mock
  private IAppointmentRepository appointmentRepository;

  @Mock
  private UnitOfWorkJpa unitOfWork;

  private AttendAppointmentHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    handler = new AttendAppointmentHandler(appointmentRepository, unitOfWork);
  }

  @Test
  void shouldReturnFailureWhenAppointmentNotFound() {
    UUID id = UUID.randomUUID();
    AttendAppointmentCommand cmd = new AttendAppointmentCommand(id, null, null, null, null);

    when(appointmentRepository.GetById(id)).thenReturn(null);

    var result = handler.handle(cmd);

    assertFalse(result.isSuccess());
    assertEquals("Appointment.NotFound", result.getError().getCode());
  }

  @Test
  void shouldAttendAppointmentSuccessfully() throws DomainException {
    UUID id = UUID.randomUUID();
    Appointment appointment = mock(Appointment.class);
    AttendAppointmentCommand cmd = new AttendAppointmentCommand(id, "Notas", null, null, null);

    when(appointmentRepository.GetById(id)).thenReturn(appointment);

    var result = handler.handle(cmd);

    verify(appointment).attend(
        eq("Notas"),
        any(),
        any(),
        any()
    );

    verify(appointmentRepository).update(appointment);
    // verify(unitOfWork).register(appointment);
    verify(unitOfWork).commitAsync();

    assertTrue(result.isSuccess());
    assertEquals(true, result.getValue());
  }

  @Test
  void shouldReturnFailureWhenAttendThrowsDomainException() throws DomainException {
    UUID id = UUID.randomUUID();
    Appointment appointment = mock(Appointment.class);
    AttendAppointmentCommand cmd = new AttendAppointmentCommand(id, "Notas", null, null, null);

    when(appointmentRepository.GetById(id)).thenReturn(appointment);
    
    DomainException ex = new DomainException(Error.failure("Test.Code", "Test message"));
    doThrow(ex).when(appointment).attend(any(), any(), any(), any());

    var result = handler.handle(cmd);

    assertFalse(result.isSuccess());
    assertEquals("Test.Code", result.getError().getCode());

    verify(appointmentRepository, never()).update(any());
    // verify(unitOfWork, never()).register(any());
    verify(unitOfWork, never()).commitAsync();
  }
}

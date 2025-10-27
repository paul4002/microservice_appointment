package edu.nur.nurtricenter_appointment.application.appointments.cancelAppointment;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;

@Component
public class CancelAppointmentHandler implements Command.Handler<CancelAppointmentCommand, ResultWithValue<Boolean>> {
  private final IAppointmentRepository appointmentRepository;
  private final IUnitOfWork unitOfWork;
  
  public CancelAppointmentHandler(IAppointmentRepository appointmentRepository, IUnitOfWork unitOfWork) {
    this.appointmentRepository = appointmentRepository;
    this.unitOfWork = unitOfWork;
  }

  @Override
  public ResultWithValue<Boolean> handle(CancelAppointmentCommand request) {
    Appointment appointment = this.appointmentRepository.GetById(request.id());
    if (appointment == null) return ResultWithValue.failure(Error.notFound("Appointment.NotFound", "The appointment was not found", request.id().toString()));
    try {
      appointment.cancel();
    } catch (DomainException e) {
      Error err = e.getError();
      return ResultWithValue.failure(Error.failure(err.getCode(), err.getStructuredMessage(), request.id().toString()));
    }
    this.appointmentRepository.update(appointment);
    this.unitOfWork.commitAsync();
    return ResultWithValue.success(true);
  }
}

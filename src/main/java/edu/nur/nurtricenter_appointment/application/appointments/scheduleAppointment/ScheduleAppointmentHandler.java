package edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.IAppointmentRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.patients.IPatientRepository;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;
import edu.nur.nurtricenter_appointment.core.results.Error;

@Component
public class ScheduleAppointmentHandler implements Command.Handler<ScheduleAppointmentCommand, ResultWithValue<UUID>> {
  private final IAppointmentRepository appointmentRepository;
  private final IPatientRepository patientRepository;
  private final INutritionistRepository nutritionistRepository;
  private final IUnitOfWork unitOfWork;
  
  public ScheduleAppointmentHandler(IAppointmentRepository appointmentRepository, IPatientRepository patientRepository, INutritionistRepository nutritionistRepository, IUnitOfWork unitOfWork) {
    this.appointmentRepository = appointmentRepository;
    this.patientRepository = patientRepository;
    this.nutritionistRepository = nutritionistRepository;
    this.unitOfWork = unitOfWork;
  }

  @Override
  public ResultWithValue<UUID> handle(ScheduleAppointmentCommand request) {
    Patient patient = this.patientRepository.GetById(request.patientId());
    if (patient == null) 
      return ResultWithValue.failure(Error.notFound("Patient.NotFound", "The patient was not found", request.patientId().toString()));
    Nutritionist nutritionist = this.nutritionistRepository.GetById(request.nutritionistId());
    if (nutritionist == null) 
      return ResultWithValue.failure(Error.notFound("Nutritionist.NotFound", "The nutritionist was not found", request.nutritionistId().toString()));
    if (request.scheduleDate().isBefore(LocalDateTime.now()))
      return ResultWithValue.failure(Error.notFound("Appointment.InvalidScheduleDate", "The appointment schedule date cannot be in the past", request.scheduleDate().toString()));
    LocalDateTime start = request.scheduleDate().minusMinutes(29).plusSeconds(1);
    LocalDateTime end = request.scheduleDate().plusMinutes(29).plusSeconds(59);
    boolean existsAppointmentNearTime = this.appointmentRepository.existsAppointmentNearTime(request.nutritionistId(), start, end);
    if (existsAppointmentNearTime) 
      return ResultWithValue.failure(Error.notFound("Appointment.InvalidScheduleDate", "The appointment schedule date is not valid, nutritionist is not available", request.scheduleDate().toString()));

    try {
      AppointmentType.fromLabel(request.type());
    } catch(IllegalArgumentException e) {
      return ResultWithValue.failure(Error.notFound("Appointment.InvalidType", "The appointment type is invalid", request.type()));
    }

    Appointment appointment = Appointment.schedule(
      request.patientId(), 
      request.nutritionistId(), 
      request.scheduleDate(), 
      AppointmentType.fromLabel(request.type())
    );
    
    this.appointmentRepository.add(appointment);
    this.unitOfWork.commitAsync();
    return ResultWithValue.success(appointment.getId());
  }
}

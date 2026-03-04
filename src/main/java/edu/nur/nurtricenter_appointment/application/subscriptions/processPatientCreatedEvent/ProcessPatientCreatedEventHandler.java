package edu.nur.nurtricenter_appointment.application.subscriptions.processPatientCreatedEvent;

import java.util.UUID;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;
import edu.nur.nurtricenter_appointment.core.results.Result;
import edu.nur.nurtricenter_appointment.domain.patients.IPatientRepository;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;

@Component
public class ProcessPatientCreatedEventHandler implements Command.Handler<ProcessPatientCreatedEventCommand, Result> {
  private final IPatientRepository patientRepository;
  private final IUnitOfWork unitOfWork;

  public ProcessPatientCreatedEventHandler(IPatientRepository patientRepository, IUnitOfWork unitOfWork) {
    this.patientRepository = patientRepository;
    this.unitOfWork = unitOfWork;
  }

  @Override
  public Result handle(ProcessPatientCreatedEventCommand request) {
    if (request.payload().get("pacienteId") == null || request.payload().get("pacienteId").toString().isBlank()) {
      return Result.failure(new Error("PatientCreated.PatientIdMissing", "Patient id is required in event", ErrorType.VALIDATION, ""));
    }
    if (request.payload().get("nombre") == null || request.payload().get("nombre").toString().isBlank()) {
      return Result.failure(new Error("PatientCreated.PatientNameMissing", "Patient name is required in event", ErrorType.VALIDATION, ""));
    }
    String pacienteId = request.payload().get("pacienteId").toString();
    String name = request.payload().get("nombre").toString();
    Patient patient = new Patient(UUID.fromString(pacienteId), name);
    this.patientRepository.Add(patient);
    this.unitOfWork.commitAsync(patient);
    return Result.success();
  }
}

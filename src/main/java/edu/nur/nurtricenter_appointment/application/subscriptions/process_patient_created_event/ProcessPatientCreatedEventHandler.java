package edu.nur.nurtricenter_appointment.application.subscriptions.process_patient_created_event;

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
	private static final String FIELD_PATIENT = "pacienteId";
	private static final String FIELD_NAME = "nombre";
	private static final String FIELD_DOCUMENT = "documento";
	private final IPatientRepository patientRepository;
	private final IUnitOfWork unitOfWork;

	public ProcessPatientCreatedEventHandler(IPatientRepository patientRepository, IUnitOfWork unitOfWork) {
		this.patientRepository = patientRepository;
		this.unitOfWork = unitOfWork;
	}

	@Override
	public Result handle(ProcessPatientCreatedEventCommand request) {
		if (request.payload().get(FIELD_PATIENT) == null || request.payload().get(FIELD_PATIENT).toString().isBlank()) {
			return Result.failure(new Error("PatientCreated.PatientIdMissing", "Patient id is required in event", ErrorType.VALIDATION, ""));
		}
		if (request.payload().get(FIELD_NAME) == null || request.payload().get(FIELD_NAME).toString().isBlank()) {
			return Result.failure(new Error("PatientCreated.PatientNameMissing", "Patient name is required in event", ErrorType.VALIDATION, ""));
		}
		if (request.payload().get(FIELD_DOCUMENT) == null || request.payload().get(FIELD_DOCUMENT).toString().isBlank()) {
			return Result.failure(new Error("PatientCreated.PatientDocumentMissing", "Patient document is required in event", ErrorType.VALIDATION, ""));
		}
		String pacienteId = request.payload().get(FIELD_PATIENT).toString();
		String name = request.payload().get(FIELD_NAME).toString();
		String document = request.payload().get(FIELD_DOCUMENT).toString();
		Patient patient = new Patient(UUID.fromString(pacienteId), name, document);
		this.patientRepository.Add(patient);
		this.unitOfWork.commitAsync(patient);
		return Result.success();
	}
}

package edu.nur.nurtricenter_appointment.application.subscriptions.processPatientCreatedEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.Result;
import edu.nur.nurtricenter_appointment.domain.patients.IPatientRepository;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;

@ExtendWith(MockitoExtension.class)
public class ProcessPatientCreatedEventHandlerTest {

	@Mock
	IPatientRepository patientRepository;

	@Mock
	IUnitOfWork unitOfWork;

	@InjectMocks
	ProcessPatientCreatedEventHandler handler;

	@Test
	void handle_WithValidPayload_ShouldReturnSuccess() {
		// Arrange
		Map<String, Object> payload = new HashMap<>();
		payload.put("pacienteId", UUID.randomUUID().toString());
		payload.put("nombre", "Carlos Ramírez");
		payload.put("documento", "1234567890");
		ProcessPatientCreatedEventCommand command = new ProcessPatientCreatedEventCommand(payload);

		// Act
		Result result = handler.handle(command);

		// Assert
		assertTrue(result.isSuccess());
		verify(patientRepository, times(1)).Add(any(Patient.class));
	}

	@Test
	void handle_WhenPatientIdMissing_ShouldReturnFailure() {
		// Arrange
		Map<String, Object> payload = new HashMap<>();
		payload.put("nombre", "Carlos Ramírez");
		payload.put("documento", "1234567890");
		ProcessPatientCreatedEventCommand command = new ProcessPatientCreatedEventCommand(payload);

		// Act
		Result result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("PatientCreated.PatientIdMissing", result.getError().getCode());
		verify(patientRepository, never()).Add(any());
	}

	@Test
	void handle_WhenNameMissing_ShouldReturnFailure() {
		// Arrange
		Map<String, Object> payload = new HashMap<>();
		payload.put("pacienteId", UUID.randomUUID().toString());
		payload.put("documento", "1234567890");
		ProcessPatientCreatedEventCommand command = new ProcessPatientCreatedEventCommand(payload);

		// Act
		Result result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("PatientCreated.PatientNameMissing", result.getError().getCode());
		verify(patientRepository, never()).Add(any());
	}

	@Test
	void handle_WhenDocumentMissing_ShouldReturnFailure() {
		// Arrange
		Map<String, Object> payload = new HashMap<>();
		payload.put("pacienteId", UUID.randomUUID().toString());
		payload.put("nombre", "Carlos Ramírez");
		ProcessPatientCreatedEventCommand command = new ProcessPatientCreatedEventCommand(payload);

		// Act
		Result result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("PatientCreated.PatientDocumentMissing", result.getError().getCode());
		verify(patientRepository, never()).Add(any());
	}

	@Test
	void handle_WhenPatientIdIsBlank_ShouldReturnFailure() {
		// Arrange
		Map<String, Object> payload = new HashMap<>();
		payload.put("pacienteId", "   ");
		payload.put("nombre", "Carlos Ramírez");
		payload.put("documento", "1234567890");
		ProcessPatientCreatedEventCommand command = new ProcessPatientCreatedEventCommand(payload);

		// Act
		Result result = handler.handle(command);

		// Assert
		assertTrue(result.isFailure());
		assertEquals("PatientCreated.PatientIdMissing", result.getError().getCode());
	}
}

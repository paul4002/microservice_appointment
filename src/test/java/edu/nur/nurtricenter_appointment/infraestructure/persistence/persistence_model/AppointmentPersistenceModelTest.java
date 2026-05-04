package edu.nur.nurtricenter_appointment.infraestructure.persistence.persistence_model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;


public class AppointmentPersistenceModelTest {
	@Test
	void shouldSetAndGetFields() {
		// Arrange
		UUID id = UUID.randomUUID();
		UUID patientId = UUID.randomUUID();
		UUID nutritionistId = UUID.randomUUID();
		String type = "INITIAL";
		LocalDateTime creationDate = LocalDateTime.now();
		LocalDateTime scheduleDate = creationDate.plusDays(2);
		String status = "SCHEDULED";
		String attendance = "PENDING";

		// Act
		AppointmentPersistenceModel model = new AppointmentPersistenceModel();
		model.setId(id);
		model.setPatientId(patientId);
		model.setNutritionistId(nutritionistId);
		model.setType(type);
		model.setCreationDate(creationDate);
		model.setScheduledDate(scheduleDate);
		model.setStatus(status);
		model.setAttendance(attendance);

		// Assert
		assertEquals(id, model.getId());
		assertEquals(patientId, model.getPatientId());
		assertEquals(nutritionistId, model.getNutritionistId());
		assertEquals(type, model.getType());
		assertEquals(creationDate, model.getCreationDate());
		assertEquals(scheduleDate, model.getScheduledDate());
		assertEquals(status, model.getStatus());
		assertEquals(attendance, model.getAttendance());
	}

	@Test
	void shouldSetAndGetDiagnosisFields() {
		// Arrange
		LocalDateTime cancelDate = LocalDateTime.now();
		String notes = "Consulta cancelada";
		double weight = 70.5;
		double height = 175.0;
		double imc = 23.0;
		double bodyFat = 15.0;
		double muscleMass = 40.0;

		// Act
		AppointmentPersistenceModel model = new AppointmentPersistenceModel();
		model.setCancelDate(cancelDate);
		model.setNotes(notes);
		model.setWeight(weight);
		model.setHeight(height);
		model.setImc(imc);
		model.setBodyFat(bodyFat);
		model.setMuscleMass(muscleMass);

		// Assert
		assertEquals(cancelDate, model.getCancelDate());
		assertEquals(notes, model.getNotes());
		assertEquals(weight, model.getWeight());
		assertEquals(height, model.getHeight());
		assertEquals(imc, model.getImc());
		assertEquals(bodyFat, model.getBodyFat());
		assertEquals(muscleMass, model.getMuscleMass());
	}
}

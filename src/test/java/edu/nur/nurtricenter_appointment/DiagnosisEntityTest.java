package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.DiagnosisEntity;

public class DiagnosisEntityTest {
	@Test
	void shouldSetAndGetFields() {
		// Arrange
		UUID id = UUID.randomUUID();
		String description = "Paciente con obesidad";
		DiagnosisNutritionalState state = DiagnosisNutritionalState.OBESITY;
		String associatedRisks = "Diabetes tipo 2";
		String recommendations = "Plan alimenticio estricto";
		String goals = "Reducir IMC";
		String comments = "Paciente motivado";

		// Act
		DiagnosisEntity entity = new DiagnosisEntity();
		entity.setId(id);
		entity.setDescription(description);
		entity.setNutritionalState(state);
		entity.setAssociatedRisks(associatedRisks);
		entity.setRecommendations(recommendations);
		entity.setGoals(goals);
		entity.setComments(comments);

		// Assert
		assertEquals(id, entity.getId());
		assertEquals(description, entity.getDescription());
		assertEquals(state, entity.getNutritionalState());
		assertEquals(associatedRisks, entity.getAssociatedRisks());
		assertEquals(recommendations, entity.getRecommendations());
		assertEquals(goals, entity.getGoals());
		assertEquals(comments, entity.getComments());
	}

	@Test
	void shouldMapFromDomain() {
		// Arrange
		String description = "Paciente con obesidad";
		DiagnosisNutritionalState state = DiagnosisNutritionalState.OBESITY;
		String associatedRisks = "Diabetes tipo 2";
		String recommendations = "Plan alimenticio estricto";
		String goals = "Reducir IMC";
		String comments = "Paciente motivado";
		Diagnosis diagnosis = new Diagnosis(description, state, associatedRisks, recommendations, goals, comments);

		// Act
		DiagnosisEntity entity = DiagnosisEntity.fromDomain(diagnosis);

		// Assert
		assertEquals(description, entity.getDescription());
		assertEquals(state, entity.getNutritionalState());
		assertEquals(associatedRisks, entity.getAssociatedRisks());
		assertEquals(recommendations, entity.getRecommendations());
		assertEquals(goals, entity.getGoals());
		assertEquals(comments, entity.getComments());
	}
}

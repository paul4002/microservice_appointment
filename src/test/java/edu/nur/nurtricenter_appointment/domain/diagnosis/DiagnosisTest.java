package edu.nur.nurtricenter_appointment.domain.diagnosis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;

class DiagnosisTest {

	@Test
		void createDiagnosis_ShouldSetAllFields() {
				// Arrange
				String description = "Paciente con sobrepeso moderado";
				DiagnosisNutritionalState state = DiagnosisNutritionalState.OVERWEIGHT;
				String risks = "Riesgo cardiovascular";
				String recommendations = "Aumentar actividad física";
				String goals = "Reducir 5kg en 3 meses";
				String comments = "Revisar progreso mensual";

				// Act
				Diagnosis diagnosis = new Diagnosis(
								description,
								state,
								risks,
								recommendations,
								goals,
								comments
				);

				// Assert
				assertEquals(description, diagnosis.getDescription());
				assertEquals(state, diagnosis.getNutritionalState());
				assertEquals(risks, diagnosis.getAssociatedRisks());
				assertEquals(recommendations, diagnosis.getRecommendations());
				assertEquals(goals, diagnosis.getGoals());
				assertEquals(comments, diagnosis.getComments());
				assertNotNull(diagnosis.getId()); // heredado de Entity
		}

		@Test
		void createDiagnosis_WithBlankDescription_ShouldThrowException() {
				// Arrange
				String blank = "   ";

				// Act + Assert
				DomainException ex = assertThrows(
								DomainException.class,
								() -> new Diagnosis(
												blank,
												DiagnosisNutritionalState.NORMAL_WEIGHT,
												"ninguno",
												"mantener hábitos",
												"mantener peso",
												"sin comentarios"
								)
				);

				// Assert
				Error expectedError = DiagnosisErrors.DescriptionIsRequired();

				Error actualError = ex.getError();

				assertEquals(expectedError.getCode(), actualError.getCode());
				assertEquals(expectedError.getStructuredMessage(), actualError.getStructuredMessage());
				assertEquals(expectedError.getType(), actualError.getType());
		}

		@Test
		void constructor_ShouldGenerateRandomId() {
				// Arrange + Act
				Diagnosis d1 = new Diagnosis(
								"Desc 1",
								DiagnosisNutritionalState.NORMAL_WEIGHT,
								"Riesgo 1",
								"Reco 1",
								"Goal 1",
								"Comm 1"
				);

				Diagnosis d2 = new Diagnosis(
								"Desc 2",
								DiagnosisNutritionalState.NORMAL_WEIGHT,
								"Riesgo 2",
								"Reco 2",
								"Goal 2",
								"Comm 2"
				);

				// Assert
				assertNotEquals(d1.getId(), d2.getId());
				assertNotNull(d1.getId());
				assertNotNull(d2.getId());
		}
}

package edu.nur.nurtricenter_appointment.application.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.DiagnosisDto;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;

public class DiagnosisMapperTest {
	@Test
	void shouldReturnEmptyDiagnosisWhenDtoIsNull() {
		Diagnosis result = DiagnosisMapper.from(null);

		assertNotNull(result, "Diagnosis no debe ser null");
		assertNull(result.getDescription(), "Description debe ser null");
		assertNull(result.getNutritionalState(), "NutritionalState debe ser null");
		assertNull(result.getAssociatedRisks(), "AssociatedRisks debe ser null");
		assertNull(result.getRecommendations(), "Recommendations debe ser null");
		assertNull(result.getGoals(), "Goals debe ser null");
		assertNull(result.getComments(), "Comments debe ser null");
	}

	@Test
	void shouldMapDiagnosisDtoToDiagnosis() {
		DiagnosisDto dto = new DiagnosisDto();
		dto.description = "Test description";
		dto.nutritionalState = "Underweight";
		dto.associatedRisks = "Risk1";
		dto.recommendations = "Eat more";
		dto.goals = "Gain weight";
		dto.comments = "No comments";

		Diagnosis result = DiagnosisMapper.from(dto);

		assertNotNull(result);
		assertEquals("Test description", result.getDescription());
		assertEquals(DiagnosisNutritionalState.fromLabel("Underweight"), result.getNutritionalState());
		assertEquals("Risk1", result.getAssociatedRisks());
		assertEquals("Eat more", result.getRecommendations());
		assertEquals("Gain weight", result.getGoals());
		assertEquals("No comments", result.getComments());
	}

	@Test
	void shouldThrowExceptionForInvalidNutritionalStateLabel() {
		DiagnosisDto dto = new DiagnosisDto();
		dto.description = "Test description";
		dto.nutritionalState = "InvalidState";

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
				DiagnosisMapper.from(dto);
		});

		assertTrue(exception.getMessage().contains("Unknown label"));
	}
}

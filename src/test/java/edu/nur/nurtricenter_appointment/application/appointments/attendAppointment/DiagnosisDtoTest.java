package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class DiagnosisDtoTest {

	@Test
	void constructor_WithAllFields_ShouldStoreValues() {
		// Arrange
		String description = "Paciente con sobrepeso";
		String nutritionalState = "Overweight";
		String associatedRisks = "Riesgo cardiovascular";
		String recommendations = "Dieta balanceada";
		String goals = "Reducir 5kg en 3 meses";
		String comments = "Revisión mensual";

		// Act
		DiagnosisDto dto = new DiagnosisDto(description, nutritionalState, associatedRisks,
				recommendations, goals, comments);

		// Assert
		assertEquals(description, dto.description);
		assertEquals(nutritionalState, dto.nutritionalState);
		assertEquals(associatedRisks, dto.associatedRisks);
		assertEquals(recommendations, dto.recommendations);
		assertEquals(goals, dto.goals);
		assertEquals(comments, dto.comments);
	}

	@Test
	void defaultConstructor_ShouldCreateDtoWithNullFields() {
		// Arrange + Act
		DiagnosisDto dto = new DiagnosisDto();

		// Assert
		assertNotNull(dto);
		assertNull(dto.description);
		assertNull(dto.nutritionalState);
	}
}

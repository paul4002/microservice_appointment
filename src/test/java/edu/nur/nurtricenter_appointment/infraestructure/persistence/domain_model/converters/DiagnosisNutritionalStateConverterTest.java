package edu.nur.nurtricenter_appointment.infraestructure.persistence.domain_model.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;

class DiagnosisNutritionalStateConverterTest {
	private final DiagnosisNutritionalStateConverter converter = new DiagnosisNutritionalStateConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		DiagnosisNutritionalState state = DiagnosisNutritionalState.UNDERWEIGHT;
		// Act
		String result = converter.convertToDatabaseColumn(state);
		// Assert
		assertEquals(state.getLabel(), result);
	}

	@Test
	void shouldReturnNullWhenConvertToDatabaseColumnWithNull() {
		// Act
		String result = converter.convertToDatabaseColumn(null);
		// Assert
		assertNull(result);
	}

	@Test
	void shouldConvertToEntityAttribute() {
		// Arrange
		String label = DiagnosisNutritionalState.OVERWEIGHT.getLabel();
		// Act
		DiagnosisNutritionalState result = converter.convertToEntityAttribute(label);
		// Assert
		assertNotNull(result);
		assertEquals(DiagnosisNutritionalState.OVERWEIGHT, result);
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		DiagnosisNutritionalState result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

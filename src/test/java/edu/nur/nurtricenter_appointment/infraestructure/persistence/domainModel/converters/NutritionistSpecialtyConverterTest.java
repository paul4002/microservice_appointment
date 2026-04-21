package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.NutritionistSpecialtyConverter;

public class NutritionistSpecialtyConverterTest {
	private final NutritionistSpecialtyConverter converter = new NutritionistSpecialtyConverter();

	@Test
	void shouldConvertToDatabaseColumn() {
		// Arrange
		NutritionistSpecialty specialty = NutritionistSpecialty.FUNCTIONAL_NUTRITION;
		// Act
		String result = converter.convertToDatabaseColumn(specialty);
		// Assert
		assertEquals(specialty.getLabel(), result);
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
		String label = NutritionistSpecialty.SPORTS_NUTRITION.getLabel();
		// Act
		NutritionistSpecialty result = converter.convertToEntityAttribute(label);
		// Assert
		assertNotNull(result);
		assertEquals(NutritionistSpecialty.SPORTS_NUTRITION, result);
	}

	@Test
	void shouldReturnNullWhenConvertToEntityAttributeWithNull() {
		// Act
		NutritionistSpecialty result = converter.convertToEntityAttribute(null);
		// Assert
		assertNull(result);
	}
}

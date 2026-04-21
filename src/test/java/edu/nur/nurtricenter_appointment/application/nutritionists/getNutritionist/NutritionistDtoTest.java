package edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class NutritionistDtoTest {

	@Test
	void constructor_WithAllFields_ShouldStoreValues() {
		// Arrange
		UUID id = UUID.randomUUID();
		String name = "Carlos";
		String lastname = "Ramírez";
		String specialty = "Clinical Nutrition";
		String professionalLicense = "LIC-2024-001";

		// Act
		NutritionistDto dto = new NutritionistDto(id, name, lastname, specialty, professionalLicense);

		// Assert
		assertEquals(id, dto.id);
		assertEquals(name, dto.name);
		assertEquals(lastname, dto.lastname);
		assertEquals(specialty, dto.specialty);
		assertEquals(professionalLicense, dto.professionalLicense);
	}

	@Test
	void defaultConstructor_ShouldCreateDtoWithNullFields() {
		// Arrange + Act
		NutritionistDto dto = new NutritionistDto();

		// Assert
		assertNotNull(dto);
		assertNull(dto.id);
		assertNull(dto.name);
	}
}

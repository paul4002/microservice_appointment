package edu.nur.nurtricenter_appointment.domain.nutritionists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

public class NutritionistErrorsTest {

	@Test
	void nameIsRequired_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = NutritionistErrors.NameIsRequired();

		// Assert
		assertNotNull(error);
		assertEquals("NameIsRequired", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}

	@Test
	void lastnameIsRequired_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = NutritionistErrors.LastnameIsRequired();

		// Assert
		assertNotNull(error);
		assertEquals("LastnameIsRequired", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}

	@Test
	void professionalLicenseIsRequired_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = NutritionistErrors.ProfessionalLicenseIsRequired();

		// Assert
		assertNotNull(error);
		assertEquals("ProfessionalLicenseIsRequired", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}
}

package edu.nur.nurtricenter_appointment.domain.diagnosis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

class DiagnosisErrorsTest {

	@Test
	void descriptionIsRequired_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = DiagnosisErrors.DescriptionIsRequired();

		// Assert
		assertNotNull(error);
		assertEquals("DescriptionIsRequired", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}

	@Test
	void descriptionIsRequired_ShouldReturnNonNullDescription() {
		// Arrange + Act
		Error error = DiagnosisErrors.DescriptionIsRequired();

		// Assert
		assertNotNull(error.getDescription());
	}
}

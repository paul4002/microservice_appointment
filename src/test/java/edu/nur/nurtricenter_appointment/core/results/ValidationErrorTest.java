package edu.nur.nurtricenter_appointment.core.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ValidationErrorTest {

	@Test
	void constructor_ShouldHaveValidationCode() {
		// Arrange + Act
		ValidationError validationError = new ValidationError();

		// Assert
		assertEquals("Validation.General", validationError.getCode());
		assertEquals(ErrorType.VALIDATION, validationError.getType());
	}

	@Test
	void getErrors_ShouldReturnProvidedErrors() {
		// Arrange
		Error error1 = Error.failure("Field.Required", "Field is required");
		Error error2 = Error.failure("Field.Invalid", "Field is invalid");

		// Act
		ValidationError validationError = new ValidationError(error1, error2);
		Error[] errors = validationError.getErrors();

		// Assert
		assertEquals(2, errors.length);
		assertEquals(error1, errors[0]);
		assertEquals(error2, errors[1]);
	}

	@Test
	void getErrors_ShouldReturnCopyNotReference() {
		// Arrange
		Error error = Error.failure("Test.Code", "Test message");
		ValidationError validationError = new ValidationError(error);

		// Act
		Error[] firstCall = validationError.getErrors();
		Error[] secondCall = validationError.getErrors();

		// Assert
		assertEquals(firstCall.length, secondCall.length);
		assertEquals(firstCall[0], secondCall[0]);
	}

	@Test
	void fromResults_ShouldCollectOnlyFailedErrors() {
		// Arrange
		Error failureError = Error.failure("Test.Failure", "Failed");
		Result successResult = Result.success();
		ResultWithValue<String> failureResult = Result.failure(failureError);

		// Act
		ValidationError validationError = ValidationError.fromResults(List.of(successResult, failureResult));

		// Assert
		assertNotNull(validationError);
		assertEquals(1, validationError.getErrors().length);
		assertEquals(failureError, validationError.getErrors()[0]);
	}

	@Test
	void fromResults_WhenAllSucceed_ShouldReturnEmptyErrors() {
		// Arrange
		Result result1 = Result.success();
		Result result2 = Result.success();

		// Act
		ValidationError validationError = ValidationError.fromResults(List.of(result1, result2));

		// Assert
		assertEquals(0, validationError.getErrors().length);
	}
}

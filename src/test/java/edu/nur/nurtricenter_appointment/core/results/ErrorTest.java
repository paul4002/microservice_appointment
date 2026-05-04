package edu.nur.nurtricenter_appointment.core.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ErrorTest {

	@Test
	void none_ShouldHaveEmptyCodeAndFailureType() {
		// Arrange + Act
		Error error = Error.NONE;

		// Assert
		assertEquals("", error.getCode());
		assertEquals(ErrorType.FAILURE, error.getType());
	}

	@Test
	void nullValue_ShouldHaveExpectedCodeAndDescription() {
		// Arrange + Act
		Error error = Error.NULL_VALUE;

		// Assert
		assertEquals("General.Null", error.getCode());
		assertNotNull(error.getDescription());
	}

	@Test
	void failure_ShouldCreateErrorWithFailureType() {
		// Arrange
		String code = "Test.Failure";
		String message = "Something failed";

		// Act
		Error error = Error.failure(code, message);

		// Assert
		assertEquals(code, error.getCode());
		assertEquals(message, error.getDescription());
		assertEquals(ErrorType.FAILURE, error.getType());
	}

	@Test
	void notFound_ShouldCreateErrorWithNotFoundType() {
		// Arrange
		String code = "Test.NotFound";
		String message = "Resource not found";

		// Act
		Error error = Error.notFound(code, message);

		// Assert
		assertEquals(code, error.getCode());
		assertEquals(ErrorType.NOT_FOUND, error.getType());
	}

	@Test
	void problem_ShouldCreateErrorWithProblemType() {
		// Arrange
		String code = "Test.Problem";
		String message = "A problem occurred";

		// Act
		Error error = Error.problem(code, message);

		// Assert
		assertEquals(code, error.getCode());
		assertEquals(ErrorType.PROBLEM, error.getType());
	}

	@Test
	void conflict_ShouldCreateErrorWithConflictType() {
		// Arrange
		String code = "Test.Conflict";
		String message = "Conflict detected";

		// Act
		Error error = Error.conflict(code, message);

		// Assert
		assertEquals(code, error.getCode());
		assertEquals(ErrorType.CONFLICT, error.getType());
	}

	@Test
	void constructor_WithPlaceholderArgs_ShouldInterpolateDescription() {
		// Arrange
		String message = "Patient {nombre} not found";
		String expectedDescription = "Patient Carlos not found";

		// Act
		Error error = new Error("Test.Code", message, ErrorType.VALIDATION, "Carlos");

		// Assert
		assertEquals(expectedDescription, error.getDescription());
		assertEquals(message, error.getStructuredMessage());
	}

	@Test
	void constructor_WithNoArgs_ShouldKeepMessageAsDescription() {
		// Arrange
		String message = "Simple message";

		// Act
		Error error = new Error("Test.Code", message, ErrorType.VALIDATION);

		// Assert
		assertEquals(message, error.getDescription());
		assertEquals(message, error.getStructuredMessage());
	}
}

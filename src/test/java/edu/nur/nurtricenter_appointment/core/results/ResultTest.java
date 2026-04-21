package edu.nur.nurtricenter_appointment.core.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ResultTest {

	@Test
	void success_ShouldReturnSuccessResult() {
		// Arrange + Act
		Result result = Result.success();

		// Assert
		assertTrue(result.isSuccess());
		assertFalse(result.isFailure());
		assertEquals(Error.NONE, result.getError());
	}

	@Test
	void successWithValue_ShouldReturnSuccessResultWithValue() {
		// Arrange
		String value = "test-value";

		// Act
		ResultWithValue<String> result = Result.success(value);

		// Assert
		assertTrue(result.isSuccess());
		assertEquals(value, result.getValue());
	}

	@Test
	void failure_ShouldReturnFailureResult() {
		// Arrange
		Error error = Error.failure("Test.Failure", "Something went wrong");

		// Act
		ResultWithValue<String> result = Result.failure(error);

		// Assert
		assertTrue(result.isFailure());
		assertFalse(result.isSuccess());
		assertEquals(error, result.getError());
	}

	@Test
	void failure_ShouldReturnNullValue() {
		// Arrange
		Error error = Error.failure("Test.Failure", "Something went wrong");

		// Act
		ResultWithValue<String> result = Result.failure(error);

		// Assert
		assertEquals(null, result.getValue());
	}

	@Test
	void constructor_SuccessWithError_ShouldThrowIllegalArgumentException() {
		// Arrange + Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Result(true, Error.failure("Code", "msg")) {}
		);
	}

	@Test
	void constructor_FailureWithNone_ShouldThrowIllegalArgumentException() {
		// Arrange + Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Result(false, Error.NONE) {}
		);
	}

	@Test
	void isFailure_WhenSuccess_ShouldReturnFalse() {
		// Arrange
		Result result = Result.success();

		// Act
		boolean isFailure = result.isFailure();

		// Assert
		assertFalse(isFailure);
	}

	@Test
	void getError_WhenSuccess_ShouldReturnNone() {
		// Arrange
		Result result = Result.success();

		// Act
		Error error = result.getError();

		// Assert
		assertNotNull(error);
		assertEquals(Error.NONE, error);
	}
}

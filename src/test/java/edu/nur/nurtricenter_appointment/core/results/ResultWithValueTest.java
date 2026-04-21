package edu.nur.nurtricenter_appointment.core.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ResultWithValueTest {

	@Test
	void of_WhenValueIsNotNull_ShouldReturnSuccess() {
		// Arrange
		String value = "some-value";

		// Act
		ResultWithValue<String> result = ResultWithValue.of(value);

		// Assert
		assertTrue(result.isSuccess());
		assertEquals(value, result.getValue());
	}

	@Test
	void of_WhenValueIsNull_ShouldReturnFailureWithNullValueError() {
		// Arrange + Act
		ResultWithValue<String> result = ResultWithValue.of(null);

		// Assert
		assertTrue(result.isFailure());
		assertEquals(Error.NULL_VALUE, result.getError());
	}

	@Test
	void validationFailure_ShouldReturnFailureWithGivenError() {
		// Arrange
		Error error = Error.failure("Validation.Error", "Validation failed");

		// Act
		ResultWithValue<String> result = ResultWithValue.validationFailure(error);

		// Assert
		assertTrue(result.isFailure());
		assertEquals(error, result.getError());
		assertNull(result.getValue());
	}

	@Test
	void getValue_WhenSuccess_ShouldReturnValue() {
		// Arrange
		Integer value = 42;

		// Act
		ResultWithValue<Integer> result = Result.success(value);

		// Assert
		assertNotNull(result.getValue());
		assertEquals(value, result.getValue());
	}

	@Test
	void getValue_WhenFailure_ShouldReturnNull() {
		// Arrange
		Error error = Error.notFound("Entity.NotFound", "Not found");

		// Act
		ResultWithValue<String> result = Result.failure(error);

		// Assert
		assertNull(result.getValue());
	}

	@Test
	void isSuccess_WhenCreatedWithValue_ShouldReturnTrue() {
		// Arrange + Act
		ResultWithValue<String> result = Result.success("data");

		// Assert
		assertTrue(result.isSuccess());
		assertFalse(result.isFailure());
	}
}

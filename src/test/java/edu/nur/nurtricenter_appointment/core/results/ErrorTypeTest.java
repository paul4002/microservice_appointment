package edu.nur.nurtricenter_appointment.core.results;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ErrorTypeTest {

	@Test
	void failure_ShouldHaveCode0() {
		// Arrange + Act
		int code = ErrorType.FAILURE.getCode();

		// Assert
		assertEquals(0, code);
	}

	@Test
	void validation_ShouldHaveCode1() {
		// Arrange + Act
		int code = ErrorType.VALIDATION.getCode();

		// Assert
		assertEquals(1, code);
	}

	@Test
	void problem_ShouldHaveCode2() {
		// Arrange + Act
		int code = ErrorType.PROBLEM.getCode();

		// Assert
		assertEquals(2, code);
	}

	@Test
	void notFound_ShouldHaveCode3() {
		// Arrange + Act
		int code = ErrorType.NOT_FOUND.getCode();

		// Assert
		assertEquals(3, code);
	}

	@Test
	void conflict_ShouldHaveCode4() {
		// Arrange + Act
		int code = ErrorType.CONFLICT.getCode();

		// Assert
		assertEquals(4, code);
	}
}

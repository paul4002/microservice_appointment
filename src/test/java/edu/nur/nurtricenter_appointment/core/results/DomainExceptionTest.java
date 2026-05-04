package edu.nur.nurtricenter_appointment.core.results;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DomainExceptionTest {

	@Test
	void constructor_ShouldStoreError() {
		// Arrange
		Error error = Error.failure("Domain.Error", "Something failed in domain");

		// Act
		DomainException exception = new DomainException(error);

		// Assert
		assertEquals(error, exception.getError());
	}

	@Test
	void getError_ShouldReturnTheSameErrorProvided() {
		// Arrange
		Error error = Error.notFound("Entity.NotFound", "The entity was not found");

		// Act
		DomainException exception = new DomainException(error);

		// Assert
		assertNotNull(exception.getError());
		assertEquals(error.getCode(), exception.getError().getCode());
		assertEquals(error.getDescription(), exception.getError().getDescription());
	}

	@Test
	void domainException_ShouldExtendRuntimeException() {
		// Arrange
		Error error = Error.failure("Test.Code", "Test message");

		// Act
		DomainException exception = new DomainException(error);

		// Assert
		assertNotNull(exception);
		assertEquals(RuntimeException.class, exception.getClass().getSuperclass());
	}
}

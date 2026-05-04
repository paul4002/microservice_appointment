package edu.nur.nurtricenter_appointment.domain.patients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	void constructor_WithValidEmail_ShouldStoreValue() {
		// Arrange
		String validEmail = "usuario@example.com";

		// Act
		Email email = new Email(validEmail);

		// Assert
		assertEquals(validEmail, email.value());
	}

	@Test
	void constructor_WithValidEmailWithSubdomain_ShouldBeValid() {
		// Arrange
		String validEmail = "user.name+tag@sub.domain.org";

		// Act
		Email email = new Email(validEmail);

		// Assert
		assertEquals(validEmail, email.value());
	}

	@Test
	void constructor_WithoutAtSymbol_ShouldThrowIllegalArgumentException() {
		// Arrange
		String invalidEmail = "usuarioexample.com";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Email(invalidEmail)
		);
	}

	@Test
	void constructor_WithEmptyLocalPart_ShouldThrowIllegalArgumentException() {
		// Arrange
		String invalidEmail = "@example.com";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Email(invalidEmail)
		);
	}

	@Test
	void constructor_WithNull_ShouldThrowNullPointerException() {
		// Arrange + Act + Assert
		assertThrows(NullPointerException.class, () ->
			new Email(null)
		);
	}
}

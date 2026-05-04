package edu.nur.nurtricenter_appointment.domain.patients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CellphoneTest {

	@Test
	void constructor_WithValid8DigitNumber_ShouldStoreValue() {
		// Arrange
		String validPhone = "98765432";

		// Act
		Cellphone cellphone = new Cellphone(validPhone);

		// Assert
		assertEquals(validPhone, cellphone.value());
	}

	@Test
	void constructor_WithLessThan8Digits_ShouldThrowIllegalArgumentException() {
		// Arrange
		String shortPhone = "9876543";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Cellphone(shortPhone)
		);
	}

	@Test
	void constructor_WithMoreThan8Digits_ShouldThrowIllegalArgumentException() {
		// Arrange
		String longPhone = "987654321";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Cellphone(longPhone)
		);
	}

	@Test
	void constructor_WithLetters_ShouldThrowIllegalArgumentException() {
		// Arrange
		String withLetters = "9876543A";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Cellphone(withLetters)
		);
	}

	@Test
	void constructor_WithNull_ShouldThrowNullPointerException() {
		// Arrange + Act + Assert
		assertThrows(NullPointerException.class, () ->
			new Cellphone(null)
		);
	}
}

package edu.nur.nurtricenter_appointment.domain.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class QuantityValueTest {

	@Test
	void constructor_WithPositiveValue_ShouldStoreValue() {
		// Arrange
		int expected = 5;

		// Act
		QuantityValue quantity = new QuantityValue(expected);

		// Assert
		assertEquals(expected, quantity.value());
	}

	@Test
	void constructor_WithZero_ShouldBeValid() {
		// Arrange
		int expected = 0;

		// Act
		QuantityValue quantity = new QuantityValue(expected);

		// Assert
		assertEquals(expected, quantity.value());
	}

	@Test
	void constructor_WithNegativeValue_ShouldThrowIllegalArgumentException() {
		// Arrange
		int negativeValue = -3;

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new QuantityValue(negativeValue)
		);
	}
}

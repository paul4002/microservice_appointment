package edu.nur.nurtricenter_appointment.domain.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DecimalValueTest {

	@Test
	void constructor_WithPositiveValue_ShouldStoreValue() {
		// Arrange
		double expected = 70.5;

		// Act
		DecimalValue decimalValue = new DecimalValue(expected);

		// Assert
		assertEquals(expected, decimalValue.value());
	}

	@Test
	void constructor_WithZero_ShouldBeValid() {
		// Arrange
		double expected = 0.0;

		// Act
		DecimalValue decimalValue = new DecimalValue(expected);

		// Assert
		assertEquals(expected, decimalValue.value());
	}

	@Test
	void constructor_WithNegativeValue_ShouldThrowIllegalArgumentException() {
		// Arrange
		double negativeValue = -1.0;

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new DecimalValue(negativeValue)
		);
	}
}

package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

class PercentageTest {

	@Test
	void constructor_WithValidValue_ShouldStoreValue() {
		// Arrange
		DecimalValue decimalValue = new DecimalValue(45.5);

		// Act
		Percentage percentage = new Percentage(decimalValue);

		// Assert
		assertEquals(decimalValue, percentage.value());
	}

	@Test
	void constructor_WithZero_ShouldBeValid() {
		// Arrange
		DecimalValue decimalValue = new DecimalValue(0.0);

		// Act
		Percentage percentage = new Percentage(decimalValue);

		// Assert
		assertEquals(decimalValue, percentage.value());
	}

	@Test
	void constructor_With100_ShouldBeValid() {
		// Arrange
		DecimalValue decimalValue = new DecimalValue(100.0);

		// Act
		Percentage percentage = new Percentage(decimalValue);

		// Assert
		assertEquals(decimalValue, percentage.value());
	}

	@Test
	void constructor_WithValueGreaterThan100_ShouldThrowIllegalArgumentException() {
		// Arrange
		DecimalValue decimalValue = new DecimalValue(100.1);

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			new Percentage(decimalValue)
		);
	}
}

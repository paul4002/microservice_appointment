package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AppointmentTypeTest {

	@Test
	void initial_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentType.INITIAL.getLabel();

		// Assert
		assertEquals("Initial", label);
	}

	@Test
	void followup_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentType.FOLLOWUP.getLabel();

		// Assert
		assertEquals("Follow up", label);
	}

	@Test
	void fromLabel_WithValidLabel_ShouldReturnCorrectEnum() {
		// Arrange
		String label = "Initial";

		// Act
		AppointmentType result = AppointmentType.fromLabel(label);

		// Assert
		assertEquals(AppointmentType.INITIAL, result);
	}

	@Test
	void fromLabel_CaseInsensitive_ShouldReturnCorrectEnum() {
		// Arrange
		String label = "follow up";

		// Act
		AppointmentType result = AppointmentType.fromLabel(label);

		// Assert
		assertEquals(AppointmentType.FOLLOWUP, result);
	}

	@Test
	void fromLabel_WithUnknownLabel_ShouldThrowIllegalArgumentException() {
		// Arrange
		String unknownLabel = "UNKNOWN";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			AppointmentType.fromLabel(unknownLabel)
		);
	}
}

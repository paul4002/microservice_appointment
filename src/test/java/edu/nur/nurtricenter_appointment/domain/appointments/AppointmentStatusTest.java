package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class AppointmentStatusTest {

	@Test
	void scheduled_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentStatus.SCHEDULED.getLabel();

		// Assert
		assertEquals("Scheduled", label);
	}

	@Test
	void completed_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentStatus.COMPLETED.getLabel();

		// Assert
		assertEquals("Completed", label);
	}

	@Test
	void cancelled_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentStatus.CANCELLED.getLabel();

		// Assert
		assertEquals("Cancelled", label);
	}

	@Test
	void closed_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentStatus.CLOSED.getLabel();

		// Assert
		assertEquals("Closed", label);
	}

	@Test
	void fromLabel_WithValidLabel_ShouldReturnCorrectEnum() {
		// Arrange
		String label = "Scheduled";

		// Act
		AppointmentStatus result = AppointmentStatus.fromLabel(label);

		// Assert
		assertEquals(AppointmentStatus.SCHEDULED, result);
	}

	@Test
	void fromLabel_CaseInsensitive_ShouldReturnCorrectEnum() {
		// Arrange
		String label = "completed";

		// Act
		AppointmentStatus result = AppointmentStatus.fromLabel(label);

		// Assert
		assertEquals(AppointmentStatus.COMPLETED, result);
	}

	@Test
	void fromLabel_WithUnknownLabel_ShouldThrowIllegalArgumentException() {
		// Arrange
		String unknownLabel = "UNKNOWN";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			AppointmentStatus.fromLabel(unknownLabel)
		);
	}
}

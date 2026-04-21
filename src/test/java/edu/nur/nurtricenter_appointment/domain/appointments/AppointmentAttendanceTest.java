package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentAttendanceTest {

	@Test
	void attended_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentAttendance.ATTENDED.getLabel();

		// Assert
		assertEquals("Attended", label);
	}

	@Test
	void notAttended_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentAttendance.NOT_ATTENDED.getLabel();

		// Assert
		assertEquals("Not attended", label);
	}

	@Test
	void pending_ShouldHaveCorrectLabel() {
		// Arrange + Act
		String label = AppointmentAttendance.PENDING.getLabel();

		// Assert
		assertEquals("Pending", label);
	}

	@Test
	void fromLabel_WithValidLabel_ShouldReturnCorrectEnum() {
		// Arrange
		String label = "Attended";

		// Act
		AppointmentAttendance result = AppointmentAttendance.fromLabel(label);

		// Assert
		assertEquals(AppointmentAttendance.ATTENDED, result);
	}

	@Test
	void fromLabel_WithLowercaseLabel_ShouldReturnCorrectEnum() {
		// Arrange
		String label = "pending";

		// Act
		AppointmentAttendance result = AppointmentAttendance.fromLabel(label);

		// Assert
		assertEquals(AppointmentAttendance.PENDING, result);
	}

	@Test
	void fromLabel_WithUnknownLabel_ShouldThrowIllegalArgumentException() {
		// Arrange
		String unknownLabel = "UNKNOWN";

		// Act + Assert
		assertThrows(IllegalArgumentException.class, () ->
			AppointmentAttendance.fromLabel(unknownLabel)
		);
	}
}

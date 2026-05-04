package edu.nur.nurtricenter_appointment.domain.appointments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

class AppointmentErrorsTest {

	@Test
	void invalidDate_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = AppointmentErrors.InvalidDate();

		// Assert
		assertNotNull(error);
		assertEquals("InvalidDate", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}

	@Test
	void statusNotScheduled_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = AppointmentErrors.StatusNotScheduled();

		// Assert
		assertNotNull(error);
		assertEquals("StatusNotScheduled", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}

	@Test
	void attendanceNotPending_ShouldReturnErrorWithValidationCode() {
		// Arrange + Act
		Error error = AppointmentErrors.AttendanceNotPending();

		// Assert
		assertNotNull(error);
		assertEquals("AttendanceNotAttended", error.getCode());
		assertEquals(ErrorType.VALIDATION, error.getType());
	}
}

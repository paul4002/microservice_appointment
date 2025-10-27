package edu.nur.nurtricenter_appointment.domain.appointments;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

public class AppointmentErrors {
  static Error InvalidDate() {
    return new Error("InvalidDate", "The appointment date cannot be in the past", ErrorType.VALIDATION);
  }

  static Error StatusNotScheduled() {
    return new Error("StatusNotScheduled", "The appointment should be on scheduled status", ErrorType.VALIDATION);
  }

  static Error AttendanceNotPending() {
    return new Error("AttendanceNotAttended", "The appointment attendance should be on pending", ErrorType.VALIDATION);
  }
}

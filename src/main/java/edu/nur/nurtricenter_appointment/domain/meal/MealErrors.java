package edu.nur.nurtricenter_appointment.domain.meal;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

public class MealErrors {
  static Error NameIsRequired() {
    return new Error("NameIsRequired", "The meal name is required", ErrorType.VALIDATION);
  }
}

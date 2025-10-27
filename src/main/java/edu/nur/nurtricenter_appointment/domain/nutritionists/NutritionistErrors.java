package edu.nur.nurtricenter_appointment.domain.nutritionists;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

public class NutritionistErrors {
  static Error NameIsRequired() {
    return new Error("NameIsRequired", "The nutritionist name is required", ErrorType.VALIDATION);
  }

  static Error LastnameIsRequired() {
    return new Error("LastnameIsRequired", "The nutritionist lastname is required", ErrorType.VALIDATION);
  }

  static Error ProfessionalLicenseIsRequired() {
    return new Error("ProfessionalLicenseIsRequired", "The nutritionist professional license is required", ErrorType.VALIDATION);
  }
}

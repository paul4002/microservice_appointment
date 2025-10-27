package edu.nur.nurtricenter_appointment.domain.mealplans;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

public class MealPlanErrors {
  static Error InvalidDate() {
    return new Error("InvalidStartDate", "The meal plan start/end date should be greater than now", ErrorType.VALIDATION);
  }
  
  static Error InvalidDateRange() {
    return new Error("InvalidDateRange", "The meal plan start date cannot be greater than endDate", ErrorType.VALIDATION);
  }

  static Error GeneralDescriptionIsRequired() {
    return new Error("GeneralDescriptionIsRequired", "The meal plan general description is required", ErrorType.VALIDATION);
  }

  static Error NutritionalGoalIsRequired() {
    return new Error("NutritionalGoalIsRequired", "The meal plan nutritional goal is required", ErrorType.VALIDATION);
  }
}

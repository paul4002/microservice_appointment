package edu.nur.nurtricenter_appointment.domain.mealplans;

import java.util.UUID;

public interface IMealPlanRepository {
  UUID add(MealPlan mealPlan);
}

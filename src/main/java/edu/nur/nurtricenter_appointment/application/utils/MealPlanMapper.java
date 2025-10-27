package edu.nur.nurtricenter_appointment.application.utils;

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MealDto;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MealPlanDto;
import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;
import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;

public class MealPlanMapper {
  public static MealPlan from(MealPlanDto mealPlanDto) {
    if (mealPlanDto == null) return new MealPlan();
    MealPlan mealPlan = new MealPlan(
      mealPlanDto.generalDescription, 
      mealPlanDto.nutritionalGoal, 
      mealPlanDto.startDate, 
      mealPlanDto.endDate, 
      mealPlanDto.restrictions 
      );
    
    for(MealDto mealDto : mealPlanDto.mealDtos) {
      mealPlan.addMeal(
        mealDto.name, 
        MealSchedule.fromLabel(mealDto.schedule), 
        new QuantityValue(mealDto.totalCalories)
      );
    }

    return mealPlan;
  }
}

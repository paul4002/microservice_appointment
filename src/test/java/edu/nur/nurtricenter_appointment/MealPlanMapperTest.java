package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Date;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MealDto;
import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MealPlanDto;
import edu.nur.nurtricenter_appointment.application.utils.MealPlanMapper;
import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;

public class MealPlanMapperTest {
  @Test
  void shouldReturnEmptyMealPlanWhenDtoIsNull() {
      MealPlan result = MealPlanMapper.from(null);

      assertNotNull(result, "El MealPlan no debe ser null");
      assertNull(result.getGeneralDescription(), "generalDescription debe ser null");
      assertNull(result.getNutritionalGoal(), "nutritionalGoal debe ser null");
      assertNull(result.getStartDate(), "startDate debe ser null");
      assertNull(result.getEndDate(), "endDate debe ser null");
      assertNull(result.getRestrictions(), "restrictions debe ser null");
  }

  @Test
  void shouldMapMealPlanDtoToMealPlan() {
      MealDto mealDto1 = new MealDto();
      mealDto1.name = "Breakfast";
      mealDto1.schedule = "Breakfast";
      mealDto1.totalCalories = 300;

      MealDto mealDto2 = new MealDto();
      mealDto2.name = "Lunch";
      mealDto2.schedule = "Lunch";
      mealDto2.totalCalories = 600;

      MealPlanDto dto = new MealPlanDto();
      dto.generalDescription = "Desc";
      dto.nutritionalGoal = "Lose weight";
      dto.startDate = new Date();
      dto.endDate = new Date();
      dto.restrictions = "No sugar";
      dto.mealDtos = java.util.List.of(mealDto1, mealDto2);

      MealPlan result = MealPlanMapper.from(dto);

      assertEquals("Desc", result.getGeneralDescription());
      assertEquals("Lose weight", result.getNutritionalGoal());
      assertEquals(dto.startDate, result.getStartDate());
      assertEquals(dto.endDate, result.getEndDate());
      assertEquals("No sugar", result.getRestrictions());

      assertEquals(2, result.get_meals().size());

      assertEquals("Breakfast", result.get_meals().get(0).getName());
      assertEquals(MealSchedule.BREAKFAST, result.get_meals().get(0).getSchedule());
      assertEquals(300, result.get_meals().get(0).getCalories().value());

      assertEquals("Lunch", result.get_meals().get(1).getName());
      assertEquals(MealSchedule.LUNCH, result.get_meals().get(1).getSchedule());
      assertEquals(600, result.get_meals().get(1).getCalories().value());
  }
}

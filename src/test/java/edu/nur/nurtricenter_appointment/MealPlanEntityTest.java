package edu.nur.nurtricenter_appointment;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;
import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.MealPlanEntity;

public class MealPlanEntityTest {
  @Test
  void shouldSetAndGetFields() {
    // Arrange
    UUID id = UUID.randomUUID();
    String description = "Plan nutricional básico";
    String goal = "Bajar de peso";
    Date startDate = new Date();
    Date endDate = new Date();
    String restrictions = "Sin azúcar";
    UUID appointmentId = UUID.randomUUID();

    // Act
    MealPlanEntity entity = new MealPlanEntity();
    entity.setId(id);
    entity.setGeneralDescripcion(description);
    entity.setNutritionalGoal(goal);
    entity.setStartDate(startDate);
    entity.setEndDate(endDate);
    entity.setRestrictions(restrictions);
    entity.setAppointmentId(appointmentId);

    // Assert
    assertEquals(id, entity.getId());
    assertEquals(description, entity.getGeneralDescripcion());
    assertEquals(goal, entity.getNutritionalGoal());
    assertEquals(startDate, entity.getStartDate());
    assertEquals(endDate, entity.getEndDate());
    assertEquals(restrictions, entity.getRestrictions());
    assertEquals(appointmentId, entity.getAppointmentId());
  }

  @Test
  void shouldMapFromDomainWithMeals() {
    // Arrange
    MealPlan mealPlan = new MealPlan("Plan completo", "Subir masa muscular", new Date(), new Date(), "Ninguna");
    mealPlan.addMeal("Tostadas", MealSchedule.BREAKFAST, new QuantityValue(150));
    mealPlan.addMeal("Bife con huevo", MealSchedule.LUNCH, new QuantityValue(600));

    // Act
    MealPlanEntity entity = MealPlanEntity.fromDomain(mealPlan);

    // Assert
    assertEquals(2, entity.getMeals().size());
    assertEquals(mealPlan.get_meals().get(0).getSchedule(), entity.getMeals().get(0).getSchedule());
    assertEquals(mealPlan.get_meals().get(1).getSchedule(), entity.getMeals().get(1).getSchedule());
  }
}

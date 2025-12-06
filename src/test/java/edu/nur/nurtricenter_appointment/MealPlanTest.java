package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlanErrors;
import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;

@SpringBootTest
public class MealPlanTest {

  private Date futureDate(int daysAhead) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DAY_OF_YEAR, daysAhead);
    return cal.getTime();
  }

  @Test
  void createMealPlan_ShouldAssignAllFieldsCorrectly() {
    // Arrange
    String description = "Plan para pérdida de peso";
    String goal = "Bajar 3 kg en 2 meses";
    Date start = futureDate(1);
    Date end = futureDate(10);
    String restrictions = "Sin lácteos";

    // Act
    MealPlan mealPlan = new MealPlan(description, goal, start, end, restrictions);

    // Assert
    assertNotNull(mealPlan.getId());
    assertEquals(description, mealPlan.getGeneralDescription());
    assertEquals(goal, mealPlan.getNutritionalGoal());
    assertEquals(start, mealPlan.getStartDate());
    assertEquals(end, mealPlan.getEndDate());
    assertEquals(restrictions, mealPlan.getRestrictions());
    assertTrue(mealPlan.get_meals().isEmpty());
  }

  @Test
  void createMealPlan_WithBlankGeneralDescription_ShouldThrowDomainException() {
    DomainException ex = assertThrows(
            DomainException.class,
            () -> new MealPlan(
                    "   ",
                    "Objetivo",
                    futureDate(1),
                    futureDate(5),
                    "restricciones"
            )
    );

    Error expected = MealPlanErrors.GeneralDescriptionIsRequired();

    assertEquals(expected.getCode(), ex.getError().getCode());
    assertEquals(expected.getStructuredMessage(), ex.getError().getStructuredMessage());
    assertEquals(expected.getType(), ex.getError().getType());
  }

  @Test
  void createMealPlan_WithBlankNutritionalGoal_ShouldThrowDomainException() {
    DomainException ex = assertThrows(
            DomainException.class,
            () -> new MealPlan(
                    "Descripción",
                    "",
                    futureDate(1),
                    futureDate(5),
                    "restricciones"
            )
    );

    Error expected = MealPlanErrors.NutritionalGoalIsRequired();

    assertEquals(expected.getCode(), ex.getError().getCode());
    assertEquals(expected.getStructuredMessage(), ex.getError().getStructuredMessage());
    assertEquals(expected.getType(), ex.getError().getType());
  }

  @Test
  void createMealPlan_WithStartDateBeforeNow_ShouldThrowInvalidDate() {
    // Arrange
    Date pastDate = futureDate(-2);

    // Act
    DomainException ex = assertThrows(
            DomainException.class,
            () -> new MealPlan(
                    "Descripción",
                    "Objetivo",
                    pastDate,
                    futureDate(5),
                    "restricciones"
            )
    );

    Error expected = MealPlanErrors.InvalidDate();

    assertEquals(expected.getCode(), ex.getError().getCode());
  }

  @Test
  void createMealPlan_WithEndDateBeforeNow_ShouldThrowInvalidDate() {
    // Arrange
    Date pastDate = futureDate(-3);

    // Act
    DomainException ex = assertThrows(
            DomainException.class,
            () -> new MealPlan(
                    "Descripción",
                    "Objetivo",
                    futureDate(1),
                    pastDate,
                    "restricciones"
            )
    );

    Error expected = MealPlanErrors.InvalidDate();

    assertEquals(expected.getCode(), ex.getError().getCode());
  }

  @Test
  void createMealPlan_WhenStartDateAfterEndDate_ShouldThrowInvalidDateRange() {
    // Arrange
    Date start = futureDate(10);
    Date end = futureDate(5);

    // Act
    DomainException ex = assertThrows(
            DomainException.class,
            () -> new MealPlan(
                    "Descripción",
                    "Objetivo",
                    start,
                    end,
                    "restricciones"
            )
    );

    Error expected = MealPlanErrors.InvalidDateRange();

    assertEquals(expected.getCode(), ex.getError().getCode());
  }

  @Test
  void addMeal_ShouldAddMealToList() {
    // Arrange
    MealPlan mealPlan = new MealPlan(
            "Plan general",
            "Objetivo nutricional",
            futureDate(1),
            futureDate(10),
            "Ninguna"
    );

    QuantityValue calories = new QuantityValue(500);

    // Act
    mealPlan.addMeal("Desayuno", MealSchedule.BREAKFAST, calories);

    // Assert
    assertEquals(1, mealPlan.get_meals().size());
    assertEquals("Desayuno", mealPlan.get_meals().get(0).getName());
    assertEquals(MealSchedule.BREAKFAST, mealPlan.get_meals().get(0).getSchedule());
    assertEquals(calories, mealPlan.get_meals().get(0).getCalories());
  }

  @Test
  void setAppointmentId_ShouldAssignCorrectValue() {
    // Arrange
    MealPlan mealPlan = new MealPlan(
            "Desc",
            "Goal",
            futureDate(1),
            futureDate(10),
            "None"
    );

    UUID appointmentId = UUID.randomUUID();

    // Act
    mealPlan.setAppointmentId(appointmentId);

    // Assert
    assertEquals(appointmentId, mealPlan.getAppointmentId());
  }
}

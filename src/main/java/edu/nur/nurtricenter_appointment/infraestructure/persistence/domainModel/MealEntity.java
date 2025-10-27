package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.meal.Meal;
import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.MealScheduleConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.QuantityValueConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meals")
public class MealEntity {

  @Id
  private UUID id;

  @Column(nullable = false)
  private String name;
  @Convert(converter = MealScheduleConverter.class)
  private MealSchedule schedule;
  @Convert(converter = QuantityValueConverter.class)
  private QuantityValue totalCalories;

  @ManyToOne
  @JoinColumn(name = "meal_plan_id")
  private MealPlanEntity mealPlan;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MealSchedule getSchedule() {
    return schedule;
  }

  public void setSchedule(MealSchedule schedule) {
    this.schedule = schedule;
  }

  public QuantityValue getTotalCalories() {
    return totalCalories;
  }

  public void setTotalCalories(QuantityValue totalCalories) {
    this.totalCalories = totalCalories;
  }

  public MealPlanEntity getMealPlan() {
    return mealPlan;
  }

  public void setMealPlan(MealPlanEntity mealPlan) {
      this.mealPlan = mealPlan;
  }

  public static MealEntity fromDomain(Meal meal) {
    MealEntity mealEntity = new MealEntity();
    mealEntity.id = meal.getId();
    mealEntity.name = meal.getName();
    mealEntity.schedule = meal.getSchedule();
    mealEntity.totalCalories = meal.getCalories();
    return mealEntity;
  }
}

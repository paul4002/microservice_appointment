package edu.nur.nurtricenter_appointment.domain.meal;


import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.Entity;
import edu.nur.nurtricenter_appointment.core.results.DomainException;

public class Meal extends Entity {
  private String name;
  private MealSchedule schedule;
  private QuantityValue calories;

  public Meal(String name, MealSchedule schedule, QuantityValue calories) {
    super(UUID.randomUUID());
    if (name.isBlank()) {
      throw new DomainException(MealErrors.NameIsRequired());
    }
    this.name = name;
    this.schedule = schedule;
    this.calories = calories;
  }

  public String getName() {
    return name;
  }

  public MealSchedule getSchedule() {
    return schedule;
  }

  public QuantityValue getCalories() {
    return calories;
  }
}

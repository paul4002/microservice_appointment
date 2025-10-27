package edu.nur.nurtricenter_appointment.domain.mealplans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.meal.Meal;
import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;

import edu.nur.nurtricenter_appointment.core.abstractions.AggregateRoot;
import edu.nur.nurtricenter_appointment.core.results.DomainException;

public class MealPlan extends AggregateRoot {
  private String generalDescription;
  private String nutritionalGoal;
  private Date startDate;
  private Date endDate;
  private List<Meal> _meals;
  private String restrictions;
  private UUID appointmentId;

  public MealPlan() {
    super(UUID.randomUUID());
  }

  public MealPlan(String generalDescription, String nutritionalGoal, Date startDate, Date endDate, String restrictions) {
    super(UUID.randomUUID());
    if(generalDescription.isBlank()) {
      throw new DomainException(MealPlanErrors.GeneralDescriptionIsRequired());
    } else if (nutritionalGoal.isBlank()) {
      throw new DomainException(MealPlanErrors.NutritionalGoalIsRequired());
    } else if ((new Date()).after(startDate) || (new Date()).after(endDate)) {
      throw new DomainException(MealPlanErrors.InvalidDate());
    } else if (startDate.after(endDate)) {
      throw new DomainException(MealPlanErrors.InvalidDateRange());
    }
    this.generalDescription = generalDescription;
    this.nutritionalGoal = nutritionalGoal;
    this._meals = new LinkedList<>();
    this.startDate = startDate;
    this.endDate = endDate;
    this.restrictions = restrictions;
  }

  public void addMeal(String name, MealSchedule schedule, QuantityValue calories) {
    Meal newMeal = new Meal(name, schedule, calories);
    this._meals.add(newMeal);
  }

  public String getGeneralDescription() {
    return generalDescription;
  }

  public String getNutritionalGoal() {
    return nutritionalGoal;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getRestrictions() {
    return restrictions;
  }

  public List<Meal> get_meals() {
    return _meals;
  }

  public UUID getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(UUID appointmentId) {
    this.appointmentId = appointmentId;
  }
}

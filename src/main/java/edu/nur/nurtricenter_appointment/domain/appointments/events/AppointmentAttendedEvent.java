package edu.nur.nurtricenter_appointment.domain.appointments.events;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;

public class AppointmentAttendedEvent extends DomainEvent  {
  private UUID appointmentId;
  private MealPlan mealPlan;
  public AppointmentAttendedEvent(UUID appointmentId, MealPlan mealPlan) {
    this.appointmentId = appointmentId;
    this.mealPlan = mealPlan;
  }
  public UUID getAppointmentId() {
    return appointmentId;
  }
  public MealPlan getMealPlan() {
    return mealPlan;
  }
}

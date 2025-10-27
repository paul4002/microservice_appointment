package edu.nur.nurtricenter_appointment.application.mealplans.eventHandlers;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Notification;
import edu.nur.nurtricenter_appointment.domain.appointments.events.AppointmentAttendedEvent;
import edu.nur.nurtricenter_appointment.domain.mealplans.IMealPlanRepository;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;

@Component
public class AddMealPlanOnAppointmentAttended implements Notification.Handler<AppointmentAttendedEvent> {

  private final IMealPlanRepository mealPlanRepository;

  public AddMealPlanOnAppointmentAttended(IMealPlanRepository mealPlanRepository) {
    this.mealPlanRepository = mealPlanRepository;
  }

  @Override
  public void handle(AppointmentAttendedEvent domainEvent) {
    MealPlan mealPlan = domainEvent.getMealPlan();
    mealPlan.setAppointmentId(domainEvent.getAppointmentId());
    this.mealPlanRepository.add(mealPlan);
  }
}

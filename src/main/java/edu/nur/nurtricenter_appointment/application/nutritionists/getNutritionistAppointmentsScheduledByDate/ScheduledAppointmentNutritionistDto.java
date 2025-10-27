package edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionistAppointmentsScheduledByDate;

import java.time.LocalDateTime;

public class ScheduledAppointmentNutritionistDto {
  public String id;
  public String patientId;
  public String type;
  public LocalDateTime scheduleDate;
  public String status;
  public String attendance;
}

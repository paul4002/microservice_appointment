package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

public class MealDto {
  public String name;
  public String schedule;
  public Integer totalCalories;
  
  public MealDto() {
  }

  public MealDto(String name, String schedule, Integer totalCalories) {
    this.name = name;
    this.schedule = schedule;
    this.totalCalories = totalCalories;
  }
}

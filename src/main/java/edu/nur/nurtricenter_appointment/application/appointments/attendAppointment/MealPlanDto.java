package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MealPlanDto {
  public String generalDescription;
  public String nutritionalGoal;
  @JsonFormat(pattern = "dd-MM-yyyy")
  public Date startDate;
  @JsonFormat(pattern = "dd-MM-yyyy")
  public Date endDate;
  public String restrictions;
  public List<MealDto> mealDtos;
  
  public MealPlanDto() {
  }

  public MealPlanDto(String generalDescription, String nutritionalGoal, Date startDate, Date endDate,
      String restrictions, List<MealDto> mealDtos) {
    this.generalDescription = generalDescription;
    this.nutritionalGoal = nutritionalGoal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.restrictions = restrictions;
    this.mealDtos = mealDtos;
  }
}

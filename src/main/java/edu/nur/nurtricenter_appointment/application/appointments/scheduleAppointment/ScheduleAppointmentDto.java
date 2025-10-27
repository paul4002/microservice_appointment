package edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ScheduleAppointmentDto {
  public String patientId;
  public String nutritionistId;
  public String type;
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  public LocalDateTime scheduleDate;
  
  public ScheduleAppointmentDto() {
  }

  public ScheduleAppointmentDto(String patientId, String nutritionistId, String type, LocalDateTime scheduleDate) {
    this.patientId = patientId;
    this.nutritionistId = nutritionistId;
    this.type = type;
    this.scheduleDate = scheduleDate;
  }
}

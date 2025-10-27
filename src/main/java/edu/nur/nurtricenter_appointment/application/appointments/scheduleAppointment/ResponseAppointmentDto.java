package edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment;

import java.time.LocalDateTime;

public class ResponseAppointmentDto {
  public String id;
  public String patientId;
  public String nutritionistId;
  public String type;
  public LocalDateTime creationDate;
  public LocalDateTime scheduleDate;
  public LocalDateTime cancelDate;
  public String status;
  public String attendance;
  
  public ResponseAppointmentDto() {
  }

  public ResponseAppointmentDto(String id) {
    this.id = id;
  }

  public ResponseAppointmentDto(String patientId, String nutritionistId, String type, LocalDateTime scheduleDate) {
    this.patientId = patientId;
    this.nutritionistId = nutritionistId;
    this.type = type;
    this.scheduleDate = scheduleDate;
  }

  public ResponseAppointmentDto(String id, String patientId, String nutritionistId, String type,
      LocalDateTime creationDate, LocalDateTime scheduleDate, LocalDateTime cancelDate, String status, String attendance) {
    this.id = id;
    this.patientId = patientId;
    this.nutritionistId = nutritionistId;
    this.type = type;
    this.creationDate = creationDate;
    this.scheduleDate = scheduleDate;
    this.cancelDate = cancelDate;
    this.status = status;
    this.attendance = attendance;
  }
}

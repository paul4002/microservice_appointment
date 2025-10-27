package edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class AppointmentPersistenceModel {

  @Id
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false)
  private UUID patientId;

  @Column(nullable = false)
  private UUID nutritionistId;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private LocalDateTime creationDate;

  @Column(nullable = false)
  private LocalDateTime scheduledDate;

  private LocalDateTime cancelDate;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private String attendance;

  private String notes;

  private Double weight;

  private Double height;

  private Double imc;

  private Double bodyFat;

  private Double muscleMass;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getPatientId() {
    return patientId;
  }

  public void setPatientId(UUID patientId) {
    this.patientId = patientId;
  }

  public UUID getNutritionistId() {
    return nutritionistId;
  }

  public void setNutritionistId(UUID nutritionistId) {
    this.nutritionistId = nutritionistId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getScheduledDate() {
    return scheduledDate;
  }

  public void setScheduledDate(LocalDateTime scheduledDate) {
    this.scheduledDate = scheduledDate;
  }

  public LocalDateTime getCancelDate() {
    return cancelDate;
  }

  public void setCancelDate(LocalDateTime cancelDate) {
    this.cancelDate = cancelDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAttendance() {
    return attendance;
  }

  public void setAttendance(String attendance) {
    this.attendance = attendance;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public Double getHeight() {
    return height;
  }

  public void setHeight(Double height) {
    this.height = height;
  }

  public Double getImc() {
    return imc;
  }

  public void setImc(Double imc) {
    this.imc = imc;
  }

  public Double getBodyFat() {
    return bodyFat;
  }

  public void setBodyFat(Double bodyFat) {
    this.bodyFat = bodyFat;
  }

  public Double getMuscleMass() {
    return muscleMass;
  }

  public void setMuscleMass(Double muscleMass) {
    this.muscleMass = muscleMass;
  }  
}

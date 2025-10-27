package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;
import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.AppointmentAttendanceConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.AppointmentStatusConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.AppointmentTypeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {
  @Id
  private UUID id;
  private UUID patientId;
  private UUID nutritionistId;
  @Convert(converter = AppointmentTypeConverter.class)
  private AppointmentType type;
  private LocalDateTime creationDate;
  private LocalDateTime scheduledDate;
  private LocalDateTime cancelDate;
  @Convert(converter = AppointmentStatusConverter.class)
  private AppointmentStatus status;
  @Convert(converter = AppointmentAttendanceConverter.class)
  private AppointmentAttendance attendance;
  private String notes;
  @Embedded
  private MeasurementEntity measurement;
  
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "diagnosis_id")
  private DiagnosisEntity diagnosis;

  @Transient
  private List<DomainEvent> domainEvents = new LinkedList<>();

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
  public AppointmentType getType() {
    return type;
  }
  public void setType(AppointmentType type) {
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
  public AppointmentStatus getStatus() {
    return status;
  }
  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }
  public AppointmentAttendance getAttendance() {
    return attendance;
  }
  public void setAttendance(AppointmentAttendance attendance) {
    this.attendance = attendance;
  }
  public String getNotes() {
    return notes;
  }
  public void setNotes(String notes) {
    this.notes = notes;
  }
  public MeasurementEntity getMeasurement() {
    return measurement;
  }
  public void setMeasurement(MeasurementEntity meausrement) {
    this.measurement = meausrement;
  }

  public DiagnosisEntity getDiagnosis() {
    return diagnosis;
  }
  public void setDiagnosis(DiagnosisEntity diagnosis) {
    this.diagnosis = diagnosis;
  }
  public List<DomainEvent> getDomainEvents() {
    return domainEvents;
  }
  public void setDomainEvents(List<DomainEvent> domainEvents) {
    this.domainEvents = domainEvents;
  }

  public void clearDomainEvents() {
    this.domainEvents = new LinkedList<>();
  }

  public static AppointmentEntity fromDomain(Appointment appointment) {
    AppointmentEntity appointmentEntity = new AppointmentEntity();
    appointmentEntity.id = appointment.getId();
    appointmentEntity.patientId = appointment.getPatientId();
    appointmentEntity.nutritionistId = appointment.getNutritionistId();
    appointmentEntity.type = appointment.getType();
    appointmentEntity.creationDate = appointment.getCreationDate();
    appointmentEntity.scheduledDate = appointment.getScheduledDate();
    appointmentEntity.cancelDate = appointment.getCancelDate();
    appointmentEntity.status = appointment.getStatus();
    appointmentEntity.attendance = appointment.getAttendance();
    appointmentEntity.notes = appointment.getNotes();
    appointmentEntity.domainEvents = appointment.getDomainEvents();
    if (appointment.getMeasurement() != null) {
      MeasurementEntity measurementEntity = new MeasurementEntity();
      Measurement measurement = appointment.getMeasurement();
      measurementEntity.setWeight(measurement.getWeight());
      measurementEntity.setHeight(measurement.getHeight());
      measurementEntity.setImc(measurement.getImc());
      measurementEntity.setBodyFat(measurement.getBodyFat());
      measurementEntity.setMuscleMass(measurement.getMuscleMass());
      appointmentEntity.measurement = measurementEntity;
    }
    if (appointment.getDiagnosis() != null) {
      DiagnosisEntity diagnosisEntity = DiagnosisEntity.fromDomain(appointment.getDiagnosis());
      appointmentEntity.diagnosis = diagnosisEntity;
    }
    return appointmentEntity;
  }

  public Appointment toDomain() {
    return new Appointment(id, patientId, nutritionistId, type, creationDate, scheduledDate, cancelDate, status, attendance, notes);
  }
}

package edu.nur.nurtricenter_appointment.domain.appointments;

import java.time.LocalDateTime;
import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.appointments.events.AppointmentAttendedEvent;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;
import edu.nur.nurtricenter_appointment.core.abstractions.AggregateRoot;
import edu.nur.nurtricenter_appointment.core.results.DomainException;

public class Appointment extends AggregateRoot {
  private UUID patientId;
  private UUID nutritionistId;
  private AppointmentType type;
  private LocalDateTime creationDate;
  private LocalDateTime scheduledDate;
  private LocalDateTime cancelDate;
  private AppointmentStatus status;
  private AppointmentAttendance attendance;
  private String notes;
  private Measurement measurement;
  private UUID mealPlanId;
  private Diagnosis diagnosis;

  public Appointment() {
    super(UUID.randomUUID());
  }

  public Appointment(UUID id) {
    super(id);
  }

  public Appointment(UUID id, UUID patientId, UUID nutritionistId, AppointmentType type, LocalDateTime creationDate,
      LocalDateTime scheduledDate, LocalDateTime cancelDate, AppointmentStatus status, AppointmentAttendance attendance,
      String notes) {
    super(id);
    this.patientId = patientId;
    this.nutritionistId = nutritionistId;
    this.type = type;
    this.creationDate = creationDate;
    this.scheduledDate = scheduledDate;
    this.cancelDate = cancelDate;
    this.status = status;
    this.attendance = attendance;
    this.notes = notes;
  }

  public static Appointment schedule(UUID patientId, UUID nutritionistId, LocalDateTime date, AppointmentType type) {
    if (date.isBefore(LocalDateTime.now())) {
      throw new DomainException(AppointmentErrors.InvalidDate());
    }
    Appointment appointment = new Appointment();
    appointment.patientId = patientId;
    appointment.nutritionistId = nutritionistId;
    appointment.type = type;
    appointment.creationDate = LocalDateTime.now();
    appointment.scheduledDate = date;
    appointment.status = AppointmentStatus.SCHEDULED;
    appointment.attendance = AppointmentAttendance.PENDING;
    return appointment;
  }

  public void cancel() {
    if (this.status != AppointmentStatus.SCHEDULED) {
      throw new DomainException(AppointmentErrors.StatusNotScheduled());
    }
    this.status = AppointmentStatus.CANCELLED;
    this.attendance = AppointmentAttendance.NOT_ATTENDED;
    this.cancelDate = LocalDateTime.now();
  }

  public void attend(String notes, Measurement measurement, Diagnosis diagnosis, MealPlan mealPlan) {
    if (this.status != AppointmentStatus.SCHEDULED) {
      throw new DomainException(AppointmentErrors.StatusNotScheduled());
    } else if (this.attendance != AppointmentAttendance.PENDING) {
      throw new DomainException(AppointmentErrors.AttendanceNotPending());
    }
    this.status = AppointmentStatus.COMPLETED;
    this.attendance = AppointmentAttendance.ATTENDED;
    this.notes = notes;
    this.measurement = measurement;
    this.diagnosis = diagnosis;
    this.addDomainEvent(new AppointmentAttendedEvent(id, mealPlan));
  }

  public void notAttended() {
    if (this.status != AppointmentStatus.SCHEDULED) {
      throw new DomainException(AppointmentErrors.StatusNotScheduled());
    } else if (this.attendance != AppointmentAttendance.PENDING) {
      throw new DomainException(AppointmentErrors.AttendanceNotPending());
    }
    this.status = AppointmentStatus.CLOSED;
    this.attendance = AppointmentAttendance.NOT_ATTENDED;
  }

  public UUID getPatientId() {
    return patientId;
  }

  public UUID getNutritionistId() {
    return nutritionistId;
  }

  public AppointmentType getType() {
    return type;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public LocalDateTime getScheduledDate() {
    return scheduledDate;
  }

  public LocalDateTime getCancelDate() {
    return cancelDate;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public AppointmentAttendance getAttendance() {
    return attendance;
  }

  public String getNotes() {
    return notes;
  }

  public Measurement getMeasurement() {
    return measurement;
  }

  public UUID getMealPlanId() {
    return mealPlanId;
  }

  public Diagnosis getDiagnosis() {
    return diagnosis;
  }
}

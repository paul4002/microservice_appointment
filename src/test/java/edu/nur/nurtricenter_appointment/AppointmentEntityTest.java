package edu.nur.nurtricenter_appointment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.AppointmentEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentEntityTest {
  @Test
  void shouldSetAndGetBasicFields() {
    // Arrange
    UUID id = UUID.randomUUID();
    UUID patientId = UUID.randomUUID();
    UUID nutritionistId = UUID.randomUUID();
    LocalDateTime creationDate = LocalDateTime.now();
    LocalDateTime scheduleDate = creationDate.plusDays(1);

    // Act
    AppointmentEntity entity = new AppointmentEntity();
    entity.setId(id);
    entity.setPatientId(patientId);
    entity.setNutritionistId(nutritionistId);
    entity.setCreationDate(creationDate);
    entity.setScheduledDate(scheduleDate);

    // Assert
    assertEquals(id, entity.getId());
    assertEquals(patientId, entity.getPatientId());
    assertEquals(nutritionistId, entity.getNutritionistId());
    assertEquals(creationDate, entity.getCreationDate());
    assertEquals(scheduleDate, entity.getScheduledDate());
  }

  @Test
  void shouldMapFromDomainWithoutEmbeddedObjects() {
    // Arrange
    Appointment appointment = new Appointment(
      UUID.randomUUID(),
      UUID.randomUUID(), 
      UUID.randomUUID(), 
      AppointmentType.INITIAL,
      LocalDateTime.now(),
      LocalDateTime.now().plusDays(2),
      null, 
      AppointmentStatus.SCHEDULED, 
      AppointmentAttendance.PENDING, 
      "Consulta inicial"
    );

    // Act
    AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);

    // Assert
    assertEquals(appointment.getId(), entity.getId());
    assertEquals(appointment.getPatientId(), entity.getPatientId());
    assertEquals(appointment.getNutritionistId(), entity.getNutritionistId());
    assertEquals(appointment.getType(), entity.getType());
    assertEquals(appointment.getStatus(), entity.getStatus());
    assertEquals(appointment.getAttendance(), entity.getAttendance());
    assertEquals(appointment.getNotes(), entity.getNotes());
    assertNull(entity.getMeasurement());
    assertNull(entity.getDiagnosis());
  }

  @Test
  void shouldMapMeasurementFromDomain() {
    // Arrange
    Measurement measurement = new Measurement(
      new DecimalValue(70.0), 
      new DecimalValue(175), 
      new DecimalValue(22.8),
      new Percentage(new DecimalValue(15.0)), 
      new Percentage(new DecimalValue(40.0))
    );
    Appointment appointment = Appointment.schedule(
      UUID.randomUUID(), 
      UUID.randomUUID(), 
      LocalDateTime.now().plusDays(1), 
      AppointmentType.FOLLOWUP
    );
    appointment.attend("Control", measurement, null, null);

    // Act
    AppointmentEntity entity = AppointmentEntity.fromDomain(appointment);

    // Assert
    assertNotNull(entity.getMeasurement());
    assertEquals(measurement.getWeight().value().value(), entity.getMeasurement().getWeight().value().value());
    assertEquals(measurement.getHeight().value().value(), entity.getMeasurement().getHeight().value().value());
    assertEquals(measurement.getImc().value(), entity.getMeasurement().getImc().value());
    assertEquals(measurement.getBodyFat().value().value(), entity.getMeasurement().getBodyFat().value().value());
    assertEquals(measurement.getMuscleMass().value().value(), entity.getMeasurement().getMuscleMass().value().value());
  }

  @Test
  void shouldConvertToDomain() {
    // Arrange
    AppointmentEntity entity = new AppointmentEntity();
    entity.setId(UUID.randomUUID());
    entity.setPatientId(UUID.randomUUID());
    entity.setNutritionistId(UUID.randomUUID());
    entity.setType(AppointmentType.INITIAL);
    entity.setCreationDate(LocalDateTime.now());
    entity.setScheduledDate(LocalDateTime.now().plusDays(3));
    entity.setStatus(AppointmentStatus.SCHEDULED);
    entity.setAttendance(AppointmentAttendance.PENDING);
    
    // Act
    Appointment domain = entity.toDomain();

    // Assert
    assertEquals(entity.getId(), domain.getId());
    assertEquals(entity.getPatientId(), domain.getPatientId());
    assertEquals(entity.getNutritionistId(), domain.getNutritionistId());
    assertEquals(entity.getType(), domain.getType());
    assertEquals(entity.getCreationDate(), domain.getCreationDate());
    assertEquals(entity.getScheduledDate(), domain.getScheduledDate());
    assertEquals(entity.getStatus(), domain.getStatus());
    assertEquals(entity.getAttendance(), domain.getAttendance());
  }
}

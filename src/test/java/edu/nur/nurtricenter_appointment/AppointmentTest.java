package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import edu.nur.nurtricenter_appointment.domain.appointments.Appointment;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;

@SpringBootTest
public class AppointmentTest {

  @Test
  void schedule_ShouldCreateScheduledAppointment() {
      // Arrange
      UUID patientId = UUID.randomUUID();
      UUID nutritionistId = UUID.randomUUID();
      LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

      // Act
      Appointment appointment = Appointment.schedule(patientId, nutritionistId, futureDate, AppointmentType.INITIAL);

      // Assert
      assertEquals(patientId, appointment.getPatientId());
      assertEquals(nutritionistId, appointment.getNutritionistId());
      assertEquals(AppointmentType.INITIAL, appointment.getType());
      assertEquals(futureDate, appointment.getScheduledDate());
      assertEquals(AppointmentStatus.SCHEDULED, appointment.getStatus());
      assertEquals(AppointmentAttendance.PENDING, appointment.getAttendance());
      assertNotNull(appointment.getCreationDate());
  }

  @Test
  void cancel_ShouldSetStatusCancelledAndRegisterCancelDate() {
      // Arrange
      Appointment appointment = Appointment.schedule(
              UUID.randomUUID(),
              UUID.randomUUID(),
              LocalDateTime.now().plusDays(1),
              AppointmentType.FOLLOWUP
      );

      // Act
      appointment.cancel();

      // Assert
      assertEquals(AppointmentStatus.CANCELLED, appointment.getStatus());
      assertEquals(AppointmentAttendance.NOT_ATTENDED, appointment.getAttendance());
      assertNotNull(appointment.getCancelDate());
  }

  @Test
  void attend_ShouldSetStatusCompletedAndFillFields() {
      // Arrange
      Appointment appointment = Appointment.schedule(
              UUID.randomUUID(),
              UUID.randomUUID(),
              LocalDateTime.now().plusDays(1),
              AppointmentType.FOLLOWUP
      );

      String notes = "Paciente estable";
      Measurement measurement = new Measurement();
      Diagnosis diagnosis = new Diagnosis();
      MealPlan mealPlan = new MealPlan();

      // Act
      appointment.attend(notes, measurement, diagnosis, mealPlan);

      // Assert
      assertEquals(AppointmentStatus.COMPLETED, appointment.getStatus());
      assertEquals(AppointmentAttendance.ATTENDED, appointment.getAttendance());
      assertEquals(notes, appointment.getNotes());
      assertEquals(measurement, appointment.getMeasurement());
      assertEquals(diagnosis, appointment.getDiagnosis());
  }
}

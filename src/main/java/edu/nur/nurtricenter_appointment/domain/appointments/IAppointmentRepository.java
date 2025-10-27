package edu.nur.nurtricenter_appointment.domain.appointments;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IAppointmentRepository {
  boolean existsAppointmentNearTime(UUID nutritionistId, LocalDateTime start, LocalDateTime end);
  UUID add(Appointment appointment);
  Appointment GetById(UUID id);
  void update(Appointment appointment);
}

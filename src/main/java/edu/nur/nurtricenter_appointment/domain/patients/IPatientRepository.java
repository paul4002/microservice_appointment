package edu.nur.nurtricenter_appointment.domain.patients;

import java.util.UUID;

public interface IPatientRepository {
  Patient GetById(UUID id);
}

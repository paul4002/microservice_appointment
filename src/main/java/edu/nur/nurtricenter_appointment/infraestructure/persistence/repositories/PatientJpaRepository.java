package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nur.nurtricenter_appointment.domain.patients.IPatientRepository;
import edu.nur.nurtricenter_appointment.domain.patients.Patient;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.PatientEntity;

@Repository
public class PatientJpaRepository implements IPatientRepository {

  @Autowired
  private PatientEntityRepository patientEntityRepository;

  @Override
  public Patient GetById(UUID id) {
    Optional<PatientEntity> entity = this.patientEntityRepository.findById(id);
    return entity.map(PatientEntity::toDomain).orElse(null);
  }
}

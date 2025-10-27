package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.PatientEntity;

public interface PatientEntityRepository extends CrudRepository<PatientEntity, UUID> {
}

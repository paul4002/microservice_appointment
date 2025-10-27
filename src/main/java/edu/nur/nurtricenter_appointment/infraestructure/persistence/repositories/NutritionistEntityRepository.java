package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.NutritionistEntity;
import jakarta.transaction.Transactional;

public interface NutritionistEntityRepository extends CrudRepository<NutritionistEntity, UUID> {
  @Modifying
  @Transactional
  @Query("UPDATE NutritionistEntity e SET e.state = false WHERE e.id = :id")
  void deactivateById(UUID id);
}

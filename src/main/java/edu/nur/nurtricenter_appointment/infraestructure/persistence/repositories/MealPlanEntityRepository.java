package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.MealPlanEntity;

public interface MealPlanEntityRepository extends CrudRepository<MealPlanEntity, UUID> {

}

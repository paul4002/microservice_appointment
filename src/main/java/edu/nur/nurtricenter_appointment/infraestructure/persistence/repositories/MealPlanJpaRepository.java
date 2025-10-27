package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nur.nurtricenter_appointment.domain.mealplans.IMealPlanRepository;
import edu.nur.nurtricenter_appointment.domain.mealplans.MealPlan;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.MealPlanEntity;

@Repository
public class MealPlanJpaRepository implements IMealPlanRepository {

  @Autowired
  MealPlanEntityRepository mealPlanEntityRepository;

  @Override
  public UUID add(MealPlan mealPlan) {
    MealPlanEntity entity = MealPlanEntity.fromDomain(mealPlan);
    return this.mealPlanEntityRepository.save(entity).getId();
  }
}

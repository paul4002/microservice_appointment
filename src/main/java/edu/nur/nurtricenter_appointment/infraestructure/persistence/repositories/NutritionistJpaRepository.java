package edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.NutritionistEntity;

@Repository
public class NutritionistJpaRepository implements INutritionistRepository {

  @Autowired
  private NutritionistEntityRepository nutritionistEntityRepository;

  @Override
  public UUID Add(Nutritionist nutritionist) {
    NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);
    this.nutritionistEntityRepository.save(entity);
    return nutritionist.getId();
  }

  @Override
  public void Delete(Nutritionist nutritionist) {
    NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);
    this.nutritionistEntityRepository.deactivateById(entity.getId());
  }

  @Override
  public Nutritionist GetById(UUID id) {
    Optional<NutritionistEntity> entity = this.nutritionistEntityRepository.findById(id);
    return entity.map(NutritionistEntity::toDomain).orElse(null);
  }

  @Override
  public void Update(Nutritionist nutritionist) {
    NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);
    this.nutritionistEntityRepository.save(entity);
  }
}

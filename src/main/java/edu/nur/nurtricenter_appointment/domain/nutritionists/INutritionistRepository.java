package edu.nur.nurtricenter_appointment.domain.nutritionists;

import java.util.UUID;

public interface INutritionistRepository {
  UUID Add(Nutritionist nutritionist);
  Nutritionist GetById(UUID id);
  void Update(Nutritionist nutritionist);
  void Delete(Nutritionist nutritionist);
}

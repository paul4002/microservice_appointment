package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import jakarta.persistence.AttributeConverter;

public class NutritionistSpecialtyConverter implements AttributeConverter<NutritionistSpecialty, String> {
  @Override
  public String convertToDatabaseColumn(NutritionistSpecialty attribute) {
    return attribute != null ? attribute.getLabel() : null;
  }
  @Override
  public NutritionistSpecialty convertToEntityAttribute(String dbData) {
    return dbData != null ? NutritionistSpecialty.fromLabel(dbData) : null;
  }
}

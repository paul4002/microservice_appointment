package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.meal.MealSchedule;
import jakarta.persistence.AttributeConverter;

public class MealScheduleConverter implements AttributeConverter<MealSchedule, String> {

  @Override
  public String convertToDatabaseColumn(MealSchedule attribute) {
    return attribute != null ? attribute.getLabel() : null;
  }

  @Override
  public MealSchedule convertToEntityAttribute(String dbData) {
    return dbData != null ? MealSchedule.fromLabel(dbData) : null;
  }
}

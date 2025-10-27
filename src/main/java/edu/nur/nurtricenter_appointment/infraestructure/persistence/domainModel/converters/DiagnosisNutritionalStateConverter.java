package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;
import jakarta.persistence.AttributeConverter;

public class DiagnosisNutritionalStateConverter implements AttributeConverter<DiagnosisNutritionalState, String> {

  @Override
  public String convertToDatabaseColumn(DiagnosisNutritionalState attribute) {
    return attribute != null ? attribute.getLabel() : null;
  }

  @Override
  public DiagnosisNutritionalState convertToEntityAttribute(String dbData) {
    return dbData != null ? DiagnosisNutritionalState.fromLabel(dbData) : null;
  }
}

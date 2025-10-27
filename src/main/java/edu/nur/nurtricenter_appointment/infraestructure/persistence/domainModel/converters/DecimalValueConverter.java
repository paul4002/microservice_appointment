package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import jakarta.persistence.AttributeConverter;

public class DecimalValueConverter implements AttributeConverter<DecimalValue, Double> {

  @Override
  public Double convertToDatabaseColumn(DecimalValue attribute) {
    return attribute != null ? attribute.value() : null;
  }

  @Override
  public DecimalValue convertToEntityAttribute(Double dbData) {
    return dbData != null ? new DecimalValue(dbData) : null;
  }
}

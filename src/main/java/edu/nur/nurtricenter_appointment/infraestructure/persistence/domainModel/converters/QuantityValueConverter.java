package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.shared.QuantityValue;
import jakarta.persistence.AttributeConverter;

public class QuantityValueConverter implements AttributeConverter<QuantityValue, Integer> {

  @Override
  public Integer convertToDatabaseColumn(QuantityValue attribute) {
    return attribute != null ? attribute.value() : null;
  }

  @Override
  public QuantityValue convertToEntityAttribute(Integer dbData) {
    return dbData != null ? new QuantityValue(dbData) : null;
  }
}

package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import jakarta.persistence.AttributeConverter;

public class PercentageConverter implements AttributeConverter<Percentage, Double> {

  @Override
  public Double convertToDatabaseColumn(Percentage attribute) {
    return attribute != null ? attribute.value().value() : null;
  }

  @Override
  public Percentage convertToEntityAttribute(Double dbData) {
    return dbData != null ? new Percentage(new DecimalValue(dbData)) : null;
  }
}

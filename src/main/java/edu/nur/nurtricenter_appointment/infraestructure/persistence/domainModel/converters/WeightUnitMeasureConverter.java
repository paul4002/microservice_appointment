package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasure;
import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasureName;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import jakarta.persistence.AttributeConverter;

public class WeightUnitMeasureConverter implements AttributeConverter<UnitMeasure, Double> {

  @Override
  public Double convertToDatabaseColumn(UnitMeasure attribute) {
    return attribute != null ? attribute.value().value() : null;
  }

  @Override
  public UnitMeasure convertToEntityAttribute(Double dbData) {
    return dbData != null ? new UnitMeasure(new DecimalValue(dbData), UnitMeasureName.KG) : null;
  }
  
}

package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.patients.Cellphone;
import jakarta.persistence.AttributeConverter;

public class CellphoneConverter implements AttributeConverter<Cellphone, String> {

  @Override
  public String convertToDatabaseColumn(Cellphone attribute) {
    return attribute != null ? attribute.value() : null;
  }

  @Override
  public Cellphone convertToEntityAttribute(String dbData) {
    return dbData != null ? new Cellphone(dbData) : null;
  }

}

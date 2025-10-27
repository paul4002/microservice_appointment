package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.patients.Email;
import jakarta.persistence.AttributeConverter;

public class EmailConverter implements AttributeConverter<Email, String> {

  @Override
  public String convertToDatabaseColumn(Email attribute) {
    return attribute != null ? attribute.value() : null;
  }

  @Override
  public Email convertToEntityAttribute(String dbData) {
    return dbData != null ? new Email(dbData) : null;
  }
}

package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentType;
import jakarta.persistence.AttributeConverter;

public class AppointmentTypeConverter implements AttributeConverter<AppointmentType, String> {

  @Override
  public String convertToDatabaseColumn(AppointmentType attribute) {
    return attribute != null ? attribute.getLabel() : null;
  }

  @Override
  public AppointmentType convertToEntityAttribute(String dbData) {
    return dbData != null ? AppointmentType.fromLabel(dbData) : null;
  }
}

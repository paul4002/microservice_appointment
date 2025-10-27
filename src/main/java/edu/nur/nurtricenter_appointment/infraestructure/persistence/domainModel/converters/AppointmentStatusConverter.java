package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentStatus;
import jakarta.persistence.AttributeConverter;

public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, String> {

  @Override
  public String convertToDatabaseColumn(AppointmentStatus attribute) {
    return attribute != null ? attribute.getLabel() : null;
  }

  @Override
  public AppointmentStatus convertToEntityAttribute(String dbData) {
    return dbData != null ? AppointmentStatus.fromLabel(dbData) : null;
  }
}

package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters;

import edu.nur.nurtricenter_appointment.domain.appointments.AppointmentAttendance;
import jakarta.persistence.AttributeConverter;

public class AppointmentAttendanceConverter implements AttributeConverter<AppointmentAttendance, String> {

  @Override
  public String convertToDatabaseColumn(AppointmentAttendance attribute) {
    return attribute != null ? attribute.getLabel() : null;
  }

  @Override
  public AppointmentAttendance convertToEntityAttribute(String dbData) {
    return dbData != null ? AppointmentAttendance.fromLabel(dbData) : null;
  }
}

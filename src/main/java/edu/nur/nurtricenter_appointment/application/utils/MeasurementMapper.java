package edu.nur.nurtricenter_appointment.application.utils;

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.MeasurementDto;
import edu.nur.nurtricenter_appointment.domain.appointments.Measurement;
import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

public class MeasurementMapper {
  public static Measurement from(MeasurementDto measurementDto) {
    if (measurementDto == null) return new Measurement();
    return new Measurement(
      new DecimalValue(measurementDto.weight), 
      new DecimalValue(measurementDto.height), 
      new DecimalValue(measurementDto.imc), 
      new Percentage(new DecimalValue(measurementDto.bodyFat)), 
      new Percentage(new DecimalValue(measurementDto.muscleMass)));
  }
}

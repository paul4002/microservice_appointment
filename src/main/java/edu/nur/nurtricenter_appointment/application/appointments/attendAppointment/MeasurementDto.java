package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

public class MeasurementDto {
  public Double weight;
  public Double height;
  public Double imc;
  public Double bodyFat;
  public Double muscleMass;

  public MeasurementDto() {
  }

  public MeasurementDto(Double weight, Double height, Double imc, Double bodyFac, Double muscleMass) {
    this.weight = weight;
    this.height = height;
    this.imc = imc;
    this.bodyFat = bodyFac;
    this.muscleMass = muscleMass;
  }
}

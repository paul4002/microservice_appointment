package edu.nur.nurtricenter_appointment.domain.appointments;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;


public class Measurement {
  private UnitMeasure weight; 
  private UnitMeasure height; 
  private DecimalValue imc; 
  private Percentage bodyFat;
  private Percentage muscleMass;

  public Measurement() {
  }

  public Measurement(DecimalValue weight, DecimalValue height, DecimalValue imc, Percentage bodyFat, Percentage muscleMass) {
    this.weight = new UnitMeasure(weight, UnitMeasureName.KG);
    this.height = new UnitMeasure(height, UnitMeasureName.CM);
    this.imc = imc;
    this.bodyFat = bodyFat;
    this.muscleMass = muscleMass;
  }

  public UnitMeasure getWeight() {
    return weight;
  }

  public UnitMeasure getHeight() {
    return height;
  }

  public DecimalValue getImc() {
    return imc;
  }

  public Percentage getBodyFat() {
    return bodyFat;
  }

  public Percentage getMuscleMass() {
    return muscleMass;
  }
}

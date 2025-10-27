package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import edu.nur.nurtricenter_appointment.domain.appointments.Percentage;
import edu.nur.nurtricenter_appointment.domain.appointments.UnitMeasure;
import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.DecimalValueConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.HeightUnitMeasureConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.PercentageConverter;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.WeightUnitMeasureConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

@Embeddable
public class MeasurementEntity {
  @Convert(converter = WeightUnitMeasureConverter.class)
  private UnitMeasure weight; 
  @Convert(converter = HeightUnitMeasureConverter.class)
  private UnitMeasure height; 
  @Convert(converter = DecimalValueConverter.class)
  private DecimalValue imc; 
  @Convert(converter = PercentageConverter.class)
  private Percentage bodyFat;
  @Convert(converter = PercentageConverter.class)
  private Percentage muscleMass;
  public UnitMeasure getWeight() {
    return weight;
  }
  public void setWeight(UnitMeasure weight) {
    this.weight = weight;
  }
  public UnitMeasure getHeight() {
    return height;
  }
  public void setHeight(UnitMeasure height) {
    this.height = height;
  }
  public DecimalValue getImc() {
    return imc;
  }
  public void setImc(DecimalValue imc) {
    this.imc = imc;
  }
  public Percentage getBodyFat() {
    return bodyFat;
  }
  public void setBodyFat(Percentage bodyFat) {
    this.bodyFat = bodyFat;
  }
  public Percentage getMuscleMass() {
    return muscleMass;
  }
  public void setMuscleMass(Percentage muscleMass) {
    this.muscleMass = muscleMass;
  }
}

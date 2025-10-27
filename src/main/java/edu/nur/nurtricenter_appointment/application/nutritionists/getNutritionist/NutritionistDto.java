package edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist;

import java.util.UUID;

public class NutritionistDto {
  public UUID id;
  public String name;
  public String lastname;
  public String specialty;
  public String professionalLicense;
  
  public NutritionistDto() {
  }

  public NutritionistDto(UUID id, String name, String lastname, String specialty, String professionalLicense) {
    this.id = id;
    this.name = name;
    this.lastname = lastname;
    this.specialty = specialty;
    this.professionalLicense = professionalLicense;
  }
}

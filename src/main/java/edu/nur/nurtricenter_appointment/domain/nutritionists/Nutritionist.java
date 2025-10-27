package edu.nur.nurtricenter_appointment.domain.nutritionists;

import java.util.UUID;


import edu.nur.nurtricenter_appointment.core.abstractions.AggregateRoot;
import edu.nur.nurtricenter_appointment.core.results.DomainException;

public class Nutritionist extends AggregateRoot {
  private String name;
  private String lastname;
  private NutritionistSpecialty specialty;
  private String professionalLicense;

  public Nutritionist(UUID id, String name, String lastname, NutritionistSpecialty specialty,
      String professionalLicense) {
    super(id);
    this.name = name;
    this.lastname = lastname;
    this.specialty = specialty;
    this.professionalLicense = professionalLicense;
  }

  public Nutritionist(String name, String lastname, NutritionistSpecialty specialty,
      String professionalLicense) {
    super(UUID.randomUUID());
    if (name.isBlank()) {
      throw new DomainException(NutritionistErrors.NameIsRequired());
    } else if (lastname.isBlank()) {
      throw new DomainException(NutritionistErrors.LastnameIsRequired());
    } else if (professionalLicense.isBlank()) {
      throw new DomainException(NutritionistErrors.ProfessionalLicenseIsRequired());
    }

    this.name = name;
    this.lastname = lastname;
    this.specialty = specialty;
    this.professionalLicense = professionalLicense;
  }
  public String getName() {
    return name;
  }
  public String getLastname() {
    return lastname;
  }
  public NutritionistSpecialty getSpecialty() {
    return specialty;
  }
  public String getProfessionalLicense() {
    return professionalLicense;
  }
}

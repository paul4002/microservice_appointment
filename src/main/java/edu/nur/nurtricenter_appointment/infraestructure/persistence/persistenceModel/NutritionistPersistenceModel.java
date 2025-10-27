package edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "nutritionists")
public class NutritionistPersistenceModel {
  @Id
  @Column(nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String lastname;

  @Column(nullable = false)
  private String specialty;

  @Column(nullable = false)
  private String professionalLicense;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getSpecialty() {
    return specialty;
  }

  public void setSpecialty(String specialty) {
    this.specialty = specialty;
  }

  public String getProfessionalLicense() {
    return professionalLicense;
  }

  public void setProfessionalLicense(String professionalLicense) {
    this.professionalLicense = professionalLicense;
  }
}

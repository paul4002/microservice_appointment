package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel.NutritionistPersistenceModel;

public class NutritionistPersistenceModelTest {
  @Test
  void shouldSetAndGetAllFields() {
    // Arrange
    UUID id = UUID.randomUUID();
    String name = "Carlos";
    String lastname = "Padilla";
    String specialty = "Clinical Nutrition";
    String professionalLicense = "LIC-999";

    // Act
    NutritionistPersistenceModel model = new NutritionistPersistenceModel();
    model.setId(id);
    model.setName(name);
    model.setLastname(lastname);
    model.setSpecialty(specialty);
    model.setProfessionalLicense(professionalLicense);

    // Assert
    assertEquals(id, model.getId());
    assertEquals(name, model.getName());
    assertEquals(lastname, model.getLastname());
    assertEquals(specialty, model.getSpecialty());
    assertEquals(professionalLicense, model.getProfessionalLicense());
  }
}

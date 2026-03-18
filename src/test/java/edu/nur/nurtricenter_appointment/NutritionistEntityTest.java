package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.NutritionistEntity;

public class NutritionistEntityTest {
	@Test
	void shouldSetAndGetFields() {
		// Arrange
		UUID id = UUID.randomUUID();
		String name = "Juan";
		String lastname = "Velasquez";
		NutritionistSpecialty specialty = NutritionistSpecialty.CLINICAL_NUTRITION;
		String professionalLicense = "LIC-123";

		// Act
		NutritionistEntity entity = new NutritionistEntity();
		entity.setId(id);
		entity.setName(name);
		entity.setLastname(lastname);
		entity.setSpecialty(specialty);
		entity.setProfessionalLicense(professionalLicense);
		entity.setState(true);

		// Assert
		assertEquals(id, entity.getId());
		assertEquals(name, entity.getName());
		assertEquals(lastname, entity.getLastname());
		assertEquals(specialty, entity.getSpecialty());
		assertEquals(professionalLicense, entity.getProfessionalLicense());
		assertTrue(entity.getState());
	}

	@Test
	void shouldMapFromDomain() {
		// Arrange
		Nutritionist nutritionist = new Nutritionist(
			UUID.randomUUID(),
			"Juan",
			"Velasquez",
			NutritionistSpecialty.CLINICAL_NUTRITION,
			"PRO-456"
		);

		// Act
		NutritionistEntity entity = NutritionistEntity.fromDomain(nutritionist);

		// Assert
		assertEquals(nutritionist.getId(), entity.getId());
		assertEquals(nutritionist.getName(), entity.getName());
		assertEquals(nutritionist.getLastname(), entity.getLastname());
		assertEquals(nutritionist.getSpecialty(), entity.getSpecialty());
		assertEquals(nutritionist.getProfessionalLicense(), entity.getProfessionalLicense());
		assertTrue(entity.getState());
	}

	@Test
	void shouldConvertToDomain() {
		// Arrange
		NutritionistEntity entity = new NutritionistEntity();
		entity.setId(UUID.randomUUID());
		entity.setName("Juan");
		entity.setLastname("Velasquez");
		entity.setSpecialty(NutritionistSpecialty.FUNCTIONAL_NUTRITION);
		entity.setProfessionalLicense("LIC-789");

		// Act
		Nutritionist domain = entity.toDomain();

		// Assert
		assertEquals(entity.getId(), domain.getId());
		assertEquals(entity.getName(), domain.getName());
		assertEquals(entity.getLastname(), domain.getLastname());
		assertEquals(entity.getSpecialty(), domain.getSpecialty());
		assertEquals(entity.getProfessionalLicense(), domain.getProfessionalLicense());
	}
}

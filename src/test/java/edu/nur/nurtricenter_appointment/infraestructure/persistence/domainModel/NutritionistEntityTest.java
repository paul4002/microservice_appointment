package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;

class NutritionistEntityTest {
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

	@Test
	void equals_SameObject_ShouldReturnTrue() {
		// Arrange
		NutritionistEntity entity = new NutritionistEntity();
		entity.setId(UUID.randomUUID());

		// Act + Assert
		assertEquals(entity, entity);
	}

	@Test
	void equals_EqualEntities_ShouldReturnTrue() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Juan");
		b.setLastname("Velasquez");
		b.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		b.setProfessionalLicense("LIC-001");
		b.setState(true);

		// Act + Assert
		assertEquals(a, b);
	}

	@Test
	void equals_DifferentId_ShouldReturnFalse() {
		// Arrange
		NutritionistEntity a = new NutritionistEntity();
		a.setId(UUID.randomUUID());
		a.setName("Juan");

		NutritionistEntity b = new NutritionistEntity();
		b.setId(UUID.randomUUID());
		b.setName("Juan");

		// Act + Assert
		org.junit.jupiter.api.Assertions.assertNotEquals(a, b);
	}

	@Test
	@DisplayName("equals should return false when comparing with null")
	void equals_CompareWithNull_ShouldReturnFalse() {
		// Arrange
		NutritionistEntity entity = new NutritionistEntity();
		entity.setId(UUID.randomUUID());

		// Act + Assert
		assertNotEquals(null, entity);
	}

	@Test
	@DisplayName("equals should return false when comparing with different type")
	void equals_CompareWithDifferentType_ShouldReturnFalse() {
		// Arrange
		NutritionistEntity entity = new NutritionistEntity();
		entity.setId(UUID.randomUUID());
		String differentType = "NotANutritionistEntity";

		// Act + Assert
		assertNotEquals(entity, differentType);
	}

	@Test
	@DisplayName("equals should return false when name is different")
	void equals_DifferentName_ShouldReturnFalse() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Carlos");
		b.setLastname("Velasquez");
		b.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		b.setProfessionalLicense("LIC-001");
		b.setState(true);

		// Act + Assert
		assertNotEquals(a, b);
	}

	@Test
	@DisplayName("equals should return false when lastname is different")
	void equals_DifferentLastname_ShouldReturnFalse() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Juan");
		b.setLastname("Garcia");
		b.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		b.setProfessionalLicense("LIC-001");
		b.setState(true);

		// Act + Assert
		assertNotEquals(a, b);
	}

	@Test
	@DisplayName("equals should return false when specialty is different")
	void equals_DifferentSpecialty_ShouldReturnFalse() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Juan");
		b.setLastname("Velasquez");
		b.setSpecialty(NutritionistSpecialty.FUNCTIONAL_NUTRITION);
		b.setProfessionalLicense("LIC-001");
		b.setState(true);

		// Act + Assert
		assertNotEquals(a, b);
	}

	@Test
	@DisplayName("equals should return false when professionalLicense is different")
	void equals_DifferentProfessionalLicense_ShouldReturnFalse() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Juan");
		b.setLastname("Velasquez");
		b.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		b.setProfessionalLicense("LIC-999");
		b.setState(true);

		// Act + Assert
		assertNotEquals(a, b);
	}

	@Test
	@DisplayName("equals should return false when state is different")
	void equals_DifferentState_ShouldReturnFalse() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Juan");
		b.setLastname("Velasquez");
		b.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		b.setProfessionalLicense("LIC-001");
		b.setState(false);

		// Act + Assert
		assertNotEquals(a, b);
	}

	@Test
	@DisplayName("equals should return true when all fields with null values match")
	void equals_AllFieldsNull_ShouldReturnTrue() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);

		// Act + Assert
		assertEquals(a, b);
	}

	@Test
	@DisplayName("hashCode_EqualEntities_ShouldReturnSameHash")
	void hashCode_EqualEntities_ShouldReturnSameHash() {
		// Arrange
		UUID id = UUID.randomUUID();
		NutritionistEntity a = new NutritionistEntity();
		a.setId(id);
		a.setName("Juan");
		a.setLastname("Velasquez");
		a.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		a.setProfessionalLicense("LIC-001");
		a.setState(true);

		NutritionistEntity b = new NutritionistEntity();
		b.setId(id);
		b.setName("Juan");
		b.setLastname("Velasquez");
		b.setSpecialty(NutritionistSpecialty.CLINICAL_NUTRITION);
		b.setProfessionalLicense("LIC-001");
		b.setState(true);

		// Act + Assert
		assertEquals(a.hashCode(), b.hashCode());
	}
}

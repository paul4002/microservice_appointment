package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.core.results.DomainException;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistErrors;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;

public class NutritionistTest {
	@Test
	void shouldCreateNutritionistSuccessfully() {
		NutritionistSpecialty specialty = NutritionistSpecialty.CLINICAL_NUTRITION;

		Nutritionist nutritionist = new Nutritionist(
						"Carlos",
						"Perez",
						specialty,
						"ABC-1234"
		);

		assertNotNull(nutritionist.getId());
		assertEquals("Carlos", nutritionist.getName());
		assertEquals("Perez", nutritionist.getLastname());
		assertEquals(specialty, nutritionist.getSpecialty());
		assertEquals("ABC-1234", nutritionist.getProfessionalLicense());
	}

	@Test
	void shouldThrowErrorWhenNameIsBlank() {
		DomainException ex = assertThrows(DomainException.class, () ->
			new Nutritionist(
							"",
							"Perez",
							NutritionistSpecialty.FUNCTIONAL_NUTRITION,
							"ABC-1234"
			)
		);

		Error expected = NutritionistErrors.NameIsRequired();

		assertEquals(expected.getCode(), ex.getError().getCode());
		assertEquals(expected.getStructuredMessage(), ex.getError().getStructuredMessage());
		assertEquals(expected.getType(), ex.getError().getType());
	}

	@Test
	void shouldThrowErrorWhenLastnameIsBlank() {
		DomainException ex = assertThrows(DomainException.class, () ->
			new Nutritionist(
							"Carlos",
							"",
							NutritionistSpecialty.GERIATRIC_NUTRITION,
							"ABC-1234"
			)
		);

		Error expected = NutritionistErrors.LastnameIsRequired();

		assertEquals(expected.getCode(), ex.getError().getCode());
		assertEquals(expected.getStructuredMessage(), ex.getError().getStructuredMessage());
		assertEquals(expected.getType(), ex.getError().getType());
	}

	@Test
	void shouldThrowErrorWhenProfessionalLicenseIsBlank() {
		DomainException ex = assertThrows(DomainException.class, () ->
			new Nutritionist(
							"Carlos",
							"Perez",
							NutritionistSpecialty.SPORTS_NUTRITION,
							""
			)
		);

		Error expected = NutritionistErrors.ProfessionalLicenseIsRequired();

		assertEquals(expected.getCode(), ex.getError().getCode());
		assertEquals(expected.getStructuredMessage(), ex.getError().getStructuredMessage());
		assertEquals(expected.getType(), ex.getError().getType());
	}

	@Test
	void shouldCreateNutritionistWithGivenId() {
		UUID id = UUID.randomUUID();
		NutritionistSpecialty specialty = NutritionistSpecialty.PREVENTIVE_NUTRITION;

		Nutritionist nutritionist = new Nutritionist(
						id,
						"Ana",
						"Lopez",
						specialty,
						"LIC-4444"
		);

		assertEquals(id, nutritionist.getId());
		assertEquals("Ana", nutritionist.getName());
		assertEquals("Lopez", nutritionist.getLastname());
		assertEquals(specialty, nutritionist.getSpecialty());
		assertEquals("LIC-4444", nutritionist.getProfessionalLicense());
	}
}

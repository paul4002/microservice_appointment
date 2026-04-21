package edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel.NutritionistPersistenceModel;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.NutritionistCrudRepository;
import edu.nur.nurtricenter_appointment.infraestructure.queries.nutritionists.GetNutritionistsHandler;

public class GetNutritionistsHandlerTest {
	private NutritionistCrudRepository nutritionistRepo;
	private GetNutritionistsHandler handler;

	@BeforeEach
	void setUp() {
		nutritionistRepo = mock(NutritionistCrudRepository.class);
		handler = new GetNutritionistsHandler(nutritionistRepo);
	}

	@Test
	void shouldReturnEmptyListWhenNoNutritionists() {
		when(nutritionistRepo.findAll()).thenReturn(List.of());

		var result = handler.handle(new GetNutritionistsQuery());

		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		assertTrue(result.getValue().isEmpty());
	}

	@Test
	void shouldReturnMappedNutritionists() {
		UUID id1 = UUID.randomUUID();
		UUID id2 = UUID.randomUUID();

		NutritionistPersistenceModel n1 = new NutritionistPersistenceModel();
		n1.setId(id1);
		n1.setName("Alice");
		n1.setLastname("Smith");
		n1.setSpecialty("Clinical Nutrition");
		n1.setProfessionalLicense("LIC123");

		NutritionistPersistenceModel n2 = new NutritionistPersistenceModel();
		n2.setId(id2);
		n2.setName("Bob");
		n2.setLastname("Jones");
		n2.setSpecialty("Functional Nutrition");
		n2.setProfessionalLicense("LIC456");

		when(nutritionistRepo.findAll()).thenReturn(List.of(n1, n2));

		ResultWithValue<List<NutritionistDto>> result = handler.handle(new GetNutritionistsQuery());

		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
		assertEquals(2, result.getValue().size());

		NutritionistDto dto1 = result.getValue().get(0);
		assertEquals(id1, dto1.id);
		assertEquals("Alice", dto1.name);
		assertEquals("Smith", dto1.lastname);
		assertEquals("Clinical Nutrition", dto1.specialty);
		assertEquals("LIC123", dto1.professionalLicense);

		NutritionistDto dto2 = result.getValue().get(1);
		assertEquals(id2, dto2.id);
		assertEquals("Bob", dto2.name);
		assertEquals("Jones", dto2.lastname);
		assertEquals("Functional Nutrition", dto2.specialty);
		assertEquals("LIC456", dto2.professionalLicense);
	}
}

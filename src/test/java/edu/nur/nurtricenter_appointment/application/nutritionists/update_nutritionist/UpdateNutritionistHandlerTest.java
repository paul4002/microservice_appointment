package edu.nur.nurtricenter_appointment.application.nutritionists.update_nutritionist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;

public class UpdateNutritionistHandlerTest {
	@Mock
	private INutritionistRepository nutritionistRepository;

	@Mock
	private IUnitOfWork unitOfWork;

	private UpdateNutritionistHandler handler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		handler = new UpdateNutritionistHandler(nutritionistRepository, unitOfWork);
	}

	@Test
	void shouldReturnFailureWhenNutritionistNotFound() {
		UUID id = UUID.randomUUID();
		UpdateNutritionistCommand cmd = new UpdateNutritionistCommand(
				id, "John", "Doe", "Clinical Nutrition", "LIC123"
		);

		when(nutritionistRepository.GetById(id)).thenReturn(null);

		var result = handler.handle(cmd);

		assertFalse(result.isSuccess());
		assertEquals("Nutritionist.NotFound", result.getError().getCode());
		verify(nutritionistRepository, never()).Update(any());
		verify(unitOfWork, never()).commitAsync(any(Nutritionist.class));
	}

	@Test
	void shouldReturnFailureWhenInvalidSpecialty() {
		UUID id = UUID.randomUUID();
		Nutritionist existing = mock(Nutritionist.class);
		UpdateNutritionistCommand cmd = new UpdateNutritionistCommand(
				id, "John", "Doe", "INVALID_SPECIALTY", "LIC123"
		);

		when(nutritionistRepository.GetById(id)).thenReturn(existing);

		var result = handler.handle(cmd);

		assertFalse(result.isSuccess());
		assertEquals("Nutritionist.InvalidSpecialty", result.getError().getCode());
		verify(nutritionistRepository, never()).Update(any());
		verify(unitOfWork, never()).commitAsync(any(Nutritionist.class));
	}

	@Test
	void shouldUpdateNutritionistSuccessfully() {
		UUID id = UUID.randomUUID();
		Nutritionist existing = mock(Nutritionist.class);
		UpdateNutritionistCommand cmd = new UpdateNutritionistCommand(
				id, "John", "Doe", "Clinical Nutrition", "LIC123"
		);

		when(nutritionistRepository.GetById(id)).thenReturn(existing);

		var result = handler.handle(cmd);

		verify(nutritionistRepository).Update(any());
		verify(unitOfWork).commitAsync(any(Nutritionist.class));

		assertTrue(result.isSuccess());
		assertEquals(true, result.getValue());
	}
}

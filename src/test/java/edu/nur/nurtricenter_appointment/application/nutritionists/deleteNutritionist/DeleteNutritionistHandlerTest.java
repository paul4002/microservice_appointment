package edu.nur.nurtricenter_appointment.application.nutritionists.deleteNutritionist;

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

public class DeleteNutritionistHandlerTest {
	@Mock
	private INutritionistRepository nutritionistRepository;

	@Mock
	private IUnitOfWork unitOfWork;

	private DeleteNutritionistHandler handler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		handler = new DeleteNutritionistHandler(nutritionistRepository, unitOfWork);
	}

	@Test
	void shouldReturnFailureWhenNutritionistNotFound() {
		UUID id = UUID.randomUUID();
		DeleteNutritionistCommand cmd = new DeleteNutritionistCommand(id);

		when(nutritionistRepository.GetById(id)).thenReturn(null);

		var result = handler.handle(cmd);

		assertFalse(result.isSuccess());
		assertEquals("Nutritionist.NotFound", result.getError().getCode());

		verify(nutritionistRepository, never()).Delete(any());
		verify(unitOfWork, never()).commitAsync(any(Nutritionist.class));
	}

	@Test
	void shouldDeleteNutritionistSuccessfully() {
		UUID id = UUID.randomUUID();
		Nutritionist nutritionist = mock(Nutritionist.class);
		DeleteNutritionistCommand cmd = new DeleteNutritionistCommand(id);

		when(nutritionistRepository.GetById(id)).thenReturn(nutritionist);

		var result = handler.handle(cmd);

		verify(nutritionistRepository).Delete(nutritionist);
		verify(unitOfWork).commitAsync(any(Nutritionist.class));

		assertTrue(result.isSuccess());
		assertEquals(true, result.getValue());
	}
}

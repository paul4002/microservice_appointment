package edu.nur.nurtricenter_appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.nur.nurtricenter_appointment.application.nutritionists.createNutritionist.CreateNutritionistCommand;
import edu.nur.nurtricenter_appointment.application.nutritionists.createNutritionist.CreateNutritionistHandler;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;

public class CreateNutritionistHandlerTest {
	@Mock
	private INutritionistRepository nutritionistRepository;

	@Mock
	private IUnitOfWork unitOfWork;

	private CreateNutritionistHandler handler;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		handler = new CreateNutritionistHandler(nutritionistRepository, unitOfWork);
	}

	@Test
	void shouldCreateNutritionistSuccessfully() {
		CreateNutritionistCommand cmd = new CreateNutritionistCommand(
				"John", "Doe", "Clinical Nutrition", "LIC12345"
		);

		var result = handler.handle(cmd);

		verify(nutritionistRepository).Add(any());
		verify(unitOfWork).commitAsync();

		assertTrue(result.isSuccess());
		assertNotNull(result.getValue());
	}

	@Test
	void shouldReturnFailureWhenInvalidSpecialty() {
		CreateNutritionistCommand cmd = new CreateNutritionistCommand(
				"John", "Doe", "INVALID_SPECIALTY", "LIC12345"
		);

		var result = handler.handle(cmd);

		assertFalse(result.isSuccess());
		assertEquals("Nutritionist.InvalidSpecialty", result.getError().getCode());

		verify(nutritionistRepository, never()).Add(any());
		verify(unitOfWork, never()).commitAsync();
	}
}

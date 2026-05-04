package edu.nur.nurtricenter_appointment.webapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import an.awesome.pipelinr.Pipeline;
import edu.nur.nurtricenter_appointment.application.nutritionists.create_nutritionist.CreateNutritionistCommand;
import edu.nur.nurtricenter_appointment.application.nutritionists.delete_nutritionist.DeleteNutritionistCommand;
import edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist.GetNutritionistsQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist.NutritionistDto;
import edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date.GetNutritionistAppointmentsScheduledByDateQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.get_nutritionist_appointments_scheduled_by_date.ScheduledAppointmentNutritionistDto;
import edu.nur.nurtricenter_appointment.application.nutritionists.update_nutritionist.UpdateNutritionistCommand;
import edu.nur.nurtricenter_appointment.core.results.Result;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;

@ExtendWith(MockitoExtension.class)
public class NutritionistControllerTest {

	@Mock
	Pipeline pipeline;

	@InjectMocks
	NutritionistController controller;

	@Test
	void getNutritionist_ShouldDelegateToPipelineAndReturnList() {
		// Arrange
		List<NutritionistDto> dtos = List.of(new NutritionistDto());
		ResultWithValue<List<NutritionistDto>> expected = Result.success(dtos);
		when(pipeline.send(any(GetNutritionistsQuery.class))).thenReturn(expected);

		// Act
		ResultWithValue<List<NutritionistDto>> result = controller.getNutritionist();

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(any(GetNutritionistsQuery.class));
	}

	@Test
	void createNutritionist_ShouldDelegateToPipelineAndReturnId() {
		// Arrange
		UUID expectedId = UUID.randomUUID();
		ResultWithValue<UUID> expected = Result.success(expectedId);
		CreateNutritionistCommand command = new CreateNutritionistCommand(
				"Carlos", "Ramírez", NutritionistSpecialty.CLINICAL_NUTRITION.getLabel(), "LIC-001");
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<UUID> result = controller.createNutritionist(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}

	@Test
	void updateNutritionist_ShouldDelegateToPipelineAndReturnResult() {
		// Arrange
		ResultWithValue<Boolean> expected = Result.success(Boolean.TRUE);
		UpdateNutritionistCommand command = new UpdateNutritionistCommand(
				UUID.randomUUID(), "Carlos", "Ramírez",
				NutritionistSpecialty.SPORTS_NUTRITION.getLabel(), "LIC-002");
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<Boolean> result = controller.updateNutritionist(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}

	@Test
	void deleteNutritionist_ShouldDelegateToPipelineAndReturnResult() {
		// Arrange
		ResultWithValue<Boolean> expected = Result.success(Boolean.TRUE);
		DeleteNutritionistCommand command = new DeleteNutritionistCommand(UUID.randomUUID());
		when(pipeline.send(command)).thenReturn(expected);

		// Act
		ResultWithValue<Boolean> result = controller.deleteNutritionist(command);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(command);
	}

	@Test
	void getAppointmentsByDate_ShouldDelegateToPipelineAndReturnList() {
		// Arrange
		List<ScheduledAppointmentNutritionistDto> dtos = List.of(new ScheduledAppointmentNutritionistDto());
		ResultWithValue<List<ScheduledAppointmentNutritionistDto>> expected = Result.success(dtos);
		GetNutritionistAppointmentsScheduledByDateQuery query =
				new GetNutritionistAppointmentsScheduledByDateQuery(UUID.randomUUID(), LocalDate.now());
		when(pipeline.send(query)).thenReturn(expected);

		// Act
		ResultWithValue<List<ScheduledAppointmentNutritionistDto>> result = controller.getAppointmentsByDate(query);

		// Assert
		assertEquals(expected, result);
		verify(pipeline).send(query);
	}
}

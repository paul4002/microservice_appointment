package edu.nur.nurtricenter_appointment.application.nutritionists.deleteNutritionist;

import java.util.UUID;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record DeleteNutritionistCommand(UUID id) implements Command<ResultWithValue<Boolean>>  {
}

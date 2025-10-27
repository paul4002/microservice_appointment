package edu.nur.nurtricenter_appointment.application.nutritionists.createNutritionist;

import java.util.UUID;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record CreateNutritionistCommand(String name, String lastname, String specialty, String professionalLicense) implements Command<ResultWithValue<UUID>> {
}

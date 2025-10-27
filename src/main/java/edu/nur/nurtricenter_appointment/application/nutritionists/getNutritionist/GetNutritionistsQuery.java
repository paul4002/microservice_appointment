package edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist;

import java.util.List;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public class GetNutritionistsQuery implements Command<ResultWithValue<List<NutritionistDto>>> {
  public GetNutritionistsQuery() {}
}

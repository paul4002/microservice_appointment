package edu.nur.nurtricenter_appointment.infraestructure.queries.nutritionists;

import java.util.List;

import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist.GetNutritionistsQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist.NutritionistDto;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.NutritionistCrudRepository;

@Component
public class GetNutritionistsHandler implements Command.Handler<GetNutritionistsQuery, ResultWithValue<List<NutritionistDto>>> {

  private NutritionistCrudRepository nutritionistCrudRepository;
  
  public GetNutritionistsHandler(NutritionistCrudRepository nutritionistCrudRepository) {
    this.nutritionistCrudRepository = nutritionistCrudRepository;
  }

  @Override
  public ResultWithValue<List<NutritionistDto>> handle(GetNutritionistsQuery command) {
    List<NutritionistDto> nutritionistsDto = Streamable
      .of(nutritionistCrudRepository.findAll())
      .map(nutritionist -> new NutritionistDto(
        nutritionist.getId(),
        nutritionist.getName(),
        nutritionist.getName(),
        nutritionist.getSpecialty(),
        nutritionist.getProfessionalLicense()
      ))
      .toList();
    return ResultWithValue.success(nutritionistsDto);
  }
}

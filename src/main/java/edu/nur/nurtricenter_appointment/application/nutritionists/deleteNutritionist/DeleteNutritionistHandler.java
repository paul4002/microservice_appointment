package edu.nur.nurtricenter_appointment.application.nutritionists.deleteNutritionist;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.core.results.Error;

@Component
public class DeleteNutritionistHandler implements Command.Handler<DeleteNutritionistCommand, ResultWithValue<Boolean>> {
  private final INutritionistRepository nutritionistRepository;
  private final IUnitOfWork unitOfWork;

  public DeleteNutritionistHandler(INutritionistRepository nutritionistRepository, IUnitOfWork unitOfWork) {
    this.nutritionistRepository = nutritionistRepository;
    this.unitOfWork = unitOfWork;
  }

  @Override
  public ResultWithValue<Boolean> handle(DeleteNutritionistCommand request) {
    Nutritionist dbNutritionist = this.nutritionistRepository.GetById(request.id());
    if (dbNutritionist == null) return ResultWithValue.failure(Error.notFound("Nutritionist.NotFound", "The nutritionist was not found", request.id().toString()));
    this.nutritionistRepository.Delete(dbNutritionist);
    this.unitOfWork.commitAsync();
    return ResultWithValue.success(true);
  }
}

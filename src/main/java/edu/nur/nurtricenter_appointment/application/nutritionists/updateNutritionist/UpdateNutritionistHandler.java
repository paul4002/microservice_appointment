package edu.nur.nurtricenter_appointment.application.nutritionists.updateNutritionist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import edu.nur.nurtricenter_appointment.domain.nutritionists.events.NutritionistUpdatedEvent;

@Component
public class UpdateNutritionistHandler implements Command.Handler<UpdateNutritionistCommand, ResultWithValue<Boolean>> {
	private static final Logger logger = LoggerFactory.getLogger(UpdateNutritionistHandler.class);
	private final INutritionistRepository nutritionistRepository;
	private final IUnitOfWork unitOfWork;

	public UpdateNutritionistHandler(INutritionistRepository nutritionistRepository, IUnitOfWork unitOfWork) {
		this.nutritionistRepository = nutritionistRepository;
		this.unitOfWork = unitOfWork;
	}

	@Override
	public ResultWithValue<Boolean> handle(UpdateNutritionistCommand request) {
		Nutritionist dbNutritionist = this.nutritionistRepository.GetById(request.id());
		if (dbNutritionist == null) return ResultWithValue.failure(Error.notFound("Nutritionist.NotFound", "The nutritionist was not found", request.id().toString()));
		Nutritionist nutritionist;
		try {
		nutritionist = new Nutritionist(
				request.id(),
				request.name(),
				request.lastname(),
				NutritionistSpecialty.fromLabel(request.specialty()),
				request.professionalLicense()
			);
		} catch(IllegalArgumentException e) {
			return ResultWithValue.failure(Error.notFound("Nutritionist.InvalidSpecialty", "The nutritionist specialty is invalid", request.specialty()));
		}
		this.nutritionistRepository.Update(nutritionist);
		NutritionistUpdatedEvent event = new NutritionistUpdatedEvent(nutritionist.getId(), nutritionist.getName(), nutritionist.getLastname(), nutritionist.getSpecialty(), nutritionist.getProfessionalLicense());
		nutritionist.addDomainEvent(event);
		logger.info("✓ Nutricionista actualizado - ID: {}, Nombre: {} {}, Evento: {}",
			nutritionist.getId(), nutritionist.getName(), nutritionist.getLastname(), event.getEventName());
		this.unitOfWork.commitAsync(nutritionist);
		logger.info("✓ Evento publicado a RabbitMQ - Evento: {}", event.getEventName());
		return ResultWithValue.success(true);
	}

}

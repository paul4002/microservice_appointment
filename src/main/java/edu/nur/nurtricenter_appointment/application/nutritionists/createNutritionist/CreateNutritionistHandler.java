package edu.nur.nurtricenter_appointment.application.nutritionists.createNutritionist;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.domain.nutritionists.INutritionistRepository;
import edu.nur.nurtricenter_appointment.domain.nutritionists.Nutritionist;
import edu.nur.nurtricenter_appointment.domain.nutritionists.NutritionistSpecialty;
import edu.nur.nurtricenter_appointment.domain.nutritionists.events.NutritionistCreatedEvent;
import edu.nur.nurtricenter_appointment.core.results.Error;

@Component
public class CreateNutritionistHandler implements Command.Handler<CreateNutritionistCommand, ResultWithValue<UUID>> {
	private static final Logger logger = LoggerFactory.getLogger(CreateNutritionistHandler.class);
	private final INutritionistRepository nutritionistRepository;
	private final IUnitOfWork unitOfWork;

	public CreateNutritionistHandler(INutritionistRepository nutritionistRepository, IUnitOfWork unitOfWork) {
		this.nutritionistRepository = nutritionistRepository;
		this.unitOfWork = unitOfWork;
	}

	@Override
	public ResultWithValue<UUID> handle(CreateNutritionistCommand request) {
		Nutritionist nutritionist;
		try {
		nutritionist = new Nutritionist(
				request.name(), 
				request.lastname(), 
				NutritionistSpecialty.fromLabel(request.specialty()), 
				request.professionalLicense()
			);
		} catch(IllegalArgumentException e) {
			return ResultWithValue.failure(Error.notFound("Nutritionist.InvalidSpecialty", "The nutritionist specialty is invalid", request.specialty()));
		}
		this.nutritionistRepository.Add(nutritionist);
		NutritionistCreatedEvent event = new NutritionistCreatedEvent(nutritionist.getId(), nutritionist.getName(), nutritionist.getLastname(), nutritionist.getSpecialty(), nutritionist.getProfessionalLicense());
		nutritionist.addDomainEvent(event);
		logger.info("✓ Nutricionista creado - ID: {}, Nombre: {} {}, Evento: {}",
			nutritionist.getId(), nutritionist.getName(), nutritionist.getLastname(), event.getEventName());
		this.unitOfWork.commitAsync(nutritionist);
		logger.info("✓ Evento publicado a RabbitMQ - Evento: {}", event.getEventName());
		return ResultWithValue.success(nutritionist.getId());
	}
}

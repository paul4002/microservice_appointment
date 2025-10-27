package edu.nur.nurtricenter_appointment.infraestructure.queries.nutritionistAppointments;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionistAppointmentsScheduledByDate.GetNutritionistAppointmentsScheduledByDateQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionistAppointmentsScheduledByDate.ScheduledAppointmentNutritionistDto;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel.AppointmentPersistenceModel;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.persistenceModel.NutritionistPersistenceModel;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.AppointmentCrudRepository;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.NutritionistCrudRepository;
import edu.nur.nurtricenter_appointment.core.results.Error;

@Component
public class GetNutritionistAppointmentsScheduledByDate implements Command.Handler<GetNutritionistAppointmentsScheduledByDateQuery, ResultWithValue<List<ScheduledAppointmentNutritionistDto>>> {

  private NutritionistCrudRepository nutritionistCrudRepository;
  private AppointmentCrudRepository appointmentCrudRepository;
  

  public GetNutritionistAppointmentsScheduledByDate(NutritionistCrudRepository nutritionistCrudRepository, AppointmentCrudRepository appointmentCrudRepository) {
    this.nutritionistCrudRepository = nutritionistCrudRepository;
    this.appointmentCrudRepository = appointmentCrudRepository;
  }

  @Override
  public ResultWithValue<List<ScheduledAppointmentNutritionistDto>> handle(GetNutritionistAppointmentsScheduledByDateQuery request) {
    Optional<NutritionistPersistenceModel> dbNutritionist = this.nutritionistCrudRepository.findById(request.nutritionistId());
    if (!dbNutritionist.isPresent()) return ResultWithValue.failure(Error.notFound("Nutritionist.NotFound", "The nutritionist was not found", request.nutritionistId().toString()));
    List<AppointmentPersistenceModel> appointmentsPersistence = this.appointmentCrudRepository.findByNutritionistAndDate(request.nutritionistId(), request.date());
    List<ScheduledAppointmentNutritionistDto> appointmentsDto = new LinkedList<>();
    for(AppointmentPersistenceModel appointment : appointmentsPersistence) {
      ScheduledAppointmentNutritionistDto dto = new ScheduledAppointmentNutritionistDto();
      dto.id = appointment.getId().toString();
      dto.patientId = appointment.getPatientId().toString();
      dto.scheduleDate = appointment.getScheduledDate();
      dto.type = appointment.getType();
      dto.status = appointment.getStatus();
      dto.attendance = appointment.getAttendance();
      appointmentsDto.add(dto);
    }
    return ResultWithValue.success(appointmentsDto);
  }
}

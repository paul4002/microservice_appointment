package edu.nur.nurtricenter_appointment.webapi.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import an.awesome.pipelinr.Pipeline;
import edu.nur.nurtricenter_appointment.application.nutritionists.createNutritionist.CreateNutritionistCommand;
import edu.nur.nurtricenter_appointment.application.nutritionists.deleteNutritionist.DeleteNutritionistCommand;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist.GetNutritionistsQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionist.NutritionistDto;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionistAppointmentsScheduledByDate.GetNutritionistAppointmentsScheduledByDateQuery;
import edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionistAppointmentsScheduledByDate.ScheduledAppointmentNutritionistDto;
import edu.nur.nurtricenter_appointment.application.nutritionists.updateNutritionist.UpdateNutritionistCommand;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/nutritionist")
public class NutritionistController {
  
  private final  Pipeline pipeline;

  public NutritionistController(Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @GetMapping
  public ResultWithValue<List<NutritionistDto>> getNutritionist() {
    GetNutritionistsQuery query = new GetNutritionistsQuery();
    return query.execute(pipeline);
  }

  @PostMapping
  public ResultWithValue<UUID> createNutritionist(@RequestBody CreateNutritionistCommand createNutritionistCommand) {
    return createNutritionistCommand.execute(pipeline);
  }

  @PutMapping
  public ResultWithValue<Boolean> updateNutritionist(@RequestBody UpdateNutritionistCommand command) {
    return command.execute(pipeline);
  }

  @DeleteMapping
  public ResultWithValue<Boolean> deleteNutritionist(@RequestBody DeleteNutritionistCommand command) {
    return command.execute(pipeline);
  }

  @PostMapping("/appointments")
  public ResultWithValue<List<ScheduledAppointmentNutritionistDto>> getAppointmentsByDate(@RequestBody GetNutritionistAppointmentsScheduledByDateQuery query) {
    return query.execute(pipeline);
  }
}

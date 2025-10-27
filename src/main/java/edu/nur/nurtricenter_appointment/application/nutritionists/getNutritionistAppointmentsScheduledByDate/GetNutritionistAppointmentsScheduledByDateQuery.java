package edu.nur.nurtricenter_appointment.application.nutritionists.getNutritionistAppointmentsScheduledByDate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record GetNutritionistAppointmentsScheduledByDateQuery(UUID nutritionistId, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate date) implements Command<ResultWithValue<List<ScheduledAppointmentNutritionistDto>>> {

}

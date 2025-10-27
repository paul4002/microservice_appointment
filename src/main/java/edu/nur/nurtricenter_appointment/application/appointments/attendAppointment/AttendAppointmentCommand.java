package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

import java.util.UUID;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record AttendAppointmentCommand(UUID id, String notes, MeasurementDto measurementDto, DiagnosisDto diagnosisDto, MealPlanDto mealPlanDto) implements Command<ResultWithValue<Boolean>> {
}

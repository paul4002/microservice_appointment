package edu.nur.nurtricenter_appointment.application.appointments.cancelAppointment;

import java.util.UUID;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record CancelAppointmentCommand(UUID id) implements Command<ResultWithValue<Boolean>> {
}

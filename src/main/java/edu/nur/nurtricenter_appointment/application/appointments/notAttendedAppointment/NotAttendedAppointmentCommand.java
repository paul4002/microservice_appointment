package edu.nur.nurtricenter_appointment.application.appointments.notAttendedAppointment;

import java.util.UUID;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record NotAttendedAppointmentCommand(UUID id) implements Command<ResultWithValue<Boolean>> {
}

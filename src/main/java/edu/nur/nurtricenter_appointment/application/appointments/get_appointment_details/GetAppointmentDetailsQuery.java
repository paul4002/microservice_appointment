package edu.nur.nurtricenter_appointment.application.appointments.get_appointment_details;

import java.util.UUID;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record GetAppointmentDetailsQuery(UUID appointmentId) implements Command<ResultWithValue<AppointmentDetailsDto>> {
}

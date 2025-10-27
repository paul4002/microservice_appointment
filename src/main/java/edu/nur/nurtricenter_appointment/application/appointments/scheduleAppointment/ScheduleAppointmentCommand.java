package edu.nur.nurtricenter_appointment.application.appointments.scheduleAppointment;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.ResultWithValue;

public record ScheduleAppointmentCommand(UUID patientId, UUID nutritionistId, String type, @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime scheduleDate) implements Command<ResultWithValue<UUID>> {
}

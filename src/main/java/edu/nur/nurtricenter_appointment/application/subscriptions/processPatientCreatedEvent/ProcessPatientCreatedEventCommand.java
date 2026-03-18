package edu.nur.nurtricenter_appointment.application.subscriptions.processPatientCreatedEvent;

import java.util.Map;

import an.awesome.pipelinr.Command;
import edu.nur.nurtricenter_appointment.core.results.Result;

public record ProcessPatientCreatedEventCommand(Map<String, Object> payload) implements Command<Result> {
}

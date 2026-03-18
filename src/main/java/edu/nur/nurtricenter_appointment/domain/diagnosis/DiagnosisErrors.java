package edu.nur.nurtricenter_appointment.domain.diagnosis;

import edu.nur.nurtricenter_appointment.core.results.Error;
import edu.nur.nurtricenter_appointment.core.results.ErrorType;

public class DiagnosisErrors {
	public static Error DescriptionIsRequired() {
		return new Error("DescriptionIsRequired", "The diagnosis description is required", ErrorType.VALIDATION);
	}
}

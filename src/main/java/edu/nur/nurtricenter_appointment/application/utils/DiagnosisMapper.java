package edu.nur.nurtricenter_appointment.application.utils;

import edu.nur.nurtricenter_appointment.application.appointments.attendAppointment.DiagnosisDto;
import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;

public class DiagnosisMapper {
  public static Diagnosis from(DiagnosisDto diagnosisDto) {
    if (diagnosisDto == null) return new Diagnosis();
    return new Diagnosis(
      diagnosisDto.description, 
      DiagnosisNutritionalState.fromLabel(diagnosisDto.nutritionalState),
      diagnosisDto.associatedRisks, 
      diagnosisDto.recommendations, 
      diagnosisDto.goals, 
      diagnosisDto.comments);
  }
}

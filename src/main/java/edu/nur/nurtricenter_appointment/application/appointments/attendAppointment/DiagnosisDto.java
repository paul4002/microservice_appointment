package edu.nur.nurtricenter_appointment.application.appointments.attendAppointment;

public class DiagnosisDto {
  public String description;
  public String nutritionalState;
  public String associatedRisks;
  public String recommendations;
  public String goals;
  public String comments;
  
  public DiagnosisDto() {
  }

  public DiagnosisDto(String description, String nutritionalState, String associatedRisks,
      String recommendations, String goals, String comments) {
    this.description = description;
    this.nutritionalState = nutritionalState;
    this.associatedRisks = associatedRisks;
    this.recommendations = recommendations;
    this.goals = goals;
    this.comments = comments;
  }
}

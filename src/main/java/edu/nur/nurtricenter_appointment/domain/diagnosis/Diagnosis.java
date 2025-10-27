package edu.nur.nurtricenter_appointment.domain.diagnosis;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.Entity;
import edu.nur.nurtricenter_appointment.core.results.DomainException;

public class Diagnosis extends Entity {
  private String description;
  private DiagnosisNutritionalState nutritionalState;
  private String associatedRisks;
  private String recommendations;
  private String goals;
  private String comments;

  public Diagnosis() {
    super(UUID.randomUUID());
  }

  public Diagnosis(String description, DiagnosisNutritionalState nutritionalState, String associatedRisks, String recommendations, String goals, String comments) {
    super(UUID.randomUUID());
    if (description.isBlank()) {
      throw new DomainException(DiagnosisErrors.DescriptionIsRequired());
    }
    this.description = description;
    this.nutritionalState = nutritionalState;
    this.associatedRisks = associatedRisks;
    this.recommendations = recommendations;
    this.goals = goals;
    this.comments = comments;
  }
  public String getDescription() {
    return description;
  }
  public DiagnosisNutritionalState getNutritionalState() {
    return nutritionalState;
  }
  public String getAssociatedRisks() {
    return associatedRisks;
  }
  public String getRecommendations() {
    return recommendations;
  }
  public String getGoals() {
    return goals;
  }
  public String getComments() {
    return comments;
  }
}

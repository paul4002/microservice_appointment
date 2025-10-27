package edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.domain.diagnosis.Diagnosis;
import edu.nur.nurtricenter_appointment.domain.diagnosis.DiagnosisNutritionalState;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.converters.DiagnosisNutritionalStateConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnosis")
public class DiagnosisEntity {
  @Id
  private UUID id;
  @Column(nullable = false)
  private String description;
  @Convert(converter = DiagnosisNutritionalStateConverter.class)
  private DiagnosisNutritionalState nutritionalState;
  private String associatedRisks;
  private String recommendations;
  private String goals;
  private String comments;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DiagnosisNutritionalState getNutritionalState() {
    return nutritionalState;
  }

  public void setNutritionalState(DiagnosisNutritionalState nutritionalState) {
    this.nutritionalState = nutritionalState;
  }

  public String getAssociatedRisks() {
    return associatedRisks;
  }

  public void setAssociatedRisks(String associatedRisks) {
    this.associatedRisks = associatedRisks;
  }

  public String getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(String recommendations) {
    this.recommendations = recommendations;
  }

  public String getGoals() {
    return goals;
  }

  public void setGoals(String goals) {
    this.goals = goals;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public static DiagnosisEntity fromDomain(Diagnosis diagnosis) {
    DiagnosisEntity diagnosisEntity = new DiagnosisEntity();
    diagnosisEntity.id = diagnosis.getId();
    diagnosisEntity.description = diagnosis.getDescription();
    diagnosisEntity.nutritionalState = diagnosis.getNutritionalState();
    diagnosisEntity.associatedRisks = diagnosis.getAssociatedRisks();
    diagnosisEntity.recommendations = diagnosis.getRecommendations();
    diagnosisEntity.goals = diagnosis.getGoals();
    diagnosisEntity.comments = diagnosis.getComments();
    return diagnosisEntity;
  }
}

package edu.nur.nurtricenter_appointment.domain.diagnosis;


public enum DiagnosisNutritionalState {
  UNDERWEIGHT("Underweight"), NORMAL_WEIGHT("Normal weight"), OVERWEIGHT("Overweight"), OBESITY("Obesity");

  private final String label;

  DiagnosisNutritionalState(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static DiagnosisNutritionalState fromLabel(String label) {
    for (DiagnosisNutritionalState value : values()) {
      if (value.getLabel().equalsIgnoreCase(label)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown label: " + label);
  }
}

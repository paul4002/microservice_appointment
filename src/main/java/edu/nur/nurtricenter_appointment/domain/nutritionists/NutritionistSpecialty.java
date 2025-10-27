package edu.nur.nurtricenter_appointment.domain.nutritionists;

public enum NutritionistSpecialty {
  CLINICAL_NUTRITION("Clinical Nutrition"),
  SPORTS_NUTRITION("Sports Nutrition"),
  FUNCTIONAL_NUTRITION("Functional Nutrition"),
  GERIATRIC_NUTRITION("Geriatric Nutrition"),
  PREVENTIVE_NUTRITION("Preventive Nutrition");

  private final String label;

  NutritionistSpecialty(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static NutritionistSpecialty fromLabel(String label) {
    for (NutritionistSpecialty value : values()) {
      if (value.getLabel().equalsIgnoreCase(label)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown label: " + label);
  }
}

package edu.nur.nurtricenter_appointment.domain.meal;


public enum MealSchedule {
  BREAKFAST("Breakfast"), LUNCH("Lunch"), DINNER("Dinner"), SNACK("Snack");

  private final String label;

  MealSchedule(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static MealSchedule fromLabel(String label) {
    for (MealSchedule value : values()) {
      if (value.getLabel().equalsIgnoreCase(label)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown label: " + label);
  }
}

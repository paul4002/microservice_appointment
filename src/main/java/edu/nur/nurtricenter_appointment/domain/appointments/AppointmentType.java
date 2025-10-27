package edu.nur.nurtricenter_appointment.domain.appointments;

public enum AppointmentType {
  INITIAL("Initial"), FOLLOWUP("Follow up");

  private final String label;

  AppointmentType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static AppointmentType fromLabel(String label) {
    for (AppointmentType value : values()) {
      if (value.getLabel().equalsIgnoreCase(label)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown label: " + label);
  }
}

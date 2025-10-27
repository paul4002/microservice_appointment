package edu.nur.nurtricenter_appointment.domain.appointments;

public enum AppointmentStatus {
  SCHEDULED("Scheduled"), COMPLETED("Completed"), CANCELLED("Cancelled"), CLOSED("Closed");

  private final String label;

  AppointmentStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static AppointmentStatus fromLabel(String label) {
    for (AppointmentStatus value : values()) {
      if (value.getLabel().equalsIgnoreCase(label)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown label: " + label);
  }
}

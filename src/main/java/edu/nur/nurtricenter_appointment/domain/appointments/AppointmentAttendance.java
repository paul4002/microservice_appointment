package edu.nur.nurtricenter_appointment.domain.appointments;

public enum AppointmentAttendance {
  ATTENDED("Attended"), NOT_ATTENDED("Not attended"), PENDING("Pending");

  private final String label;

  AppointmentAttendance(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public static AppointmentAttendance fromLabel(String label) {
    for (AppointmentAttendance value : values()) {
      if (value.getLabel().equalsIgnoreCase(label)) {
        return value;
      }
    }
    throw new IllegalArgumentException("Unknown label: " + label);
  }
}

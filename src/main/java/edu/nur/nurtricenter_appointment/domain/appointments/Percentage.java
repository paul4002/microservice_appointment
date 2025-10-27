package edu.nur.nurtricenter_appointment.domain.appointments;

import edu.nur.nurtricenter_appointment.domain.shared.DecimalValue;

public record Percentage(DecimalValue value) {
  public Percentage {
    if (value.value() > 100) {
      throw new IllegalArgumentException("Percentage cannot be greater than 100");
    }
  }
}

package edu.nur.nurtricenter_appointment.domain.shared;

public record DecimalValue(double value) {
  public DecimalValue {
    if (value < 0) {
      throw new IllegalArgumentException("Decimal cannot be negative");
    }
  }
}

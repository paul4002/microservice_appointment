package edu.nur.nurtricenter_appointment.domain.shared;

public record QuantityValue(int value) {
  
  public QuantityValue {
    if (value < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }
  }
}
package edu.nur.nurtricenter_appointment.core.abstractions;

import java.util.UUID;

public abstract class AggregateRoot extends Entity {
  protected AggregateRoot(UUID id) {
    super(id);
  }
}

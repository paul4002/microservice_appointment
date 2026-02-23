package edu.nur.nurtricenter_appointment.domain.nutritionists.events;

import java.util.UUID;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;

public class NutritionistDeletedEvent extends DomainEvent {
  private final UUID nutricionistaId;

  public NutritionistDeletedEvent(UUID nutricionistaId) {
    this.nutricionistaId = nutricionistaId;
  }

  public UUID getNutricionistaId() {
    return nutricionistaId;
  }

  @Override
  public String getAggregateId() {
    return nutricionistaId.toString();
  }

  @Override
  public String getAggregateType() {
    return "Nutritionist";
  }

  @Override
  public String getEventName() {
    return "citas-evaluaciones.nutricionista.deshabilitado";
  }

  @Override
  public Object getPayload() {
    return new Payload(nutricionistaId.toString());
  }

  private record Payload(String nutricionistaId) {}
}

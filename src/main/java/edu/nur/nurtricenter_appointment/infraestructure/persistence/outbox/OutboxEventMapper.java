package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;

public class OutboxEventMapper {
  private final ObjectMapper objectMapper;

  public OutboxEventMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public OutboxEventEntity toEntity(DomainEvent event) {
    OutboxEventEntity entity = new OutboxEventEntity();
    entity.setId(UUID.randomUUID());
    entity.setAggregateType(event.getAggregateType());
    entity.setAggregateId(event.getAggregateId());
    entity.setEventType(event.getEventType());
    entity.setEventName(event.getEventName());
    entity.setPayload(serialize(event.getPayload()));
    entity.setOccurredOn(event.getOccurredOn());
    entity.setProcessedOn(null);
    entity.setAttempts(0);
    entity.setNextAttemptAt(LocalDateTime.now());
    entity.setLastError(null);
    return entity;
  }

  private String serialize(Object payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize domain event", e);
    }
  }
}

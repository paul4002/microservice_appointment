package edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.MDC;

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
    entity.setEventId(event.getId() != null ? event.getId() : UUID.randomUUID());
    entity.setAggregateType(event.getAggregateType());
    entity.setAggregateId(event.getAggregateId());
    entity.setEventType(event.getEventType());
    entity.setEventName(event.getEventName());
    entity.setRoutingKey(event.getEventName());
    entity.setSchemaVersion(1);
    entity.setCorrelationId(resolveCorrelationId());
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

  private UUID resolveCorrelationId() {
    UUID parsed = tryParseUuid(MDC.get("correlation_id"));
    if (parsed != null) {
      return parsed;
    }
    parsed = tryParseUuid(MDC.get("correlationId"));
    if (parsed != null) {
      return parsed;
    }
    return UUID.randomUUID();
  }

  private UUID tryParseUuid(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    try {
      return UUID.fromString(value);
    } catch (IllegalArgumentException ex) {
      return null;
    }
  }
}
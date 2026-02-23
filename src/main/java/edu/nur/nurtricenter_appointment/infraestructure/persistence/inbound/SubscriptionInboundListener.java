package edu.nur.nurtricenter_appointment.infraestructure.persistence.inbound;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nur.nurtricenter_appointment.infraestructure.persistence.domainModel.PatientEntity;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.repositories.PatientEntityRepository;

@Component
public class SubscriptionInboundListener {
  private static final Logger log = LoggerFactory.getLogger(SubscriptionInboundListener.class);

  // private final Pipeline pipeline;
  private final ObjectMapper objectMapper;
  private final PatientEntityRepository patientEntityRepository;

  public SubscriptionInboundListener(/*Pipeline pipeline,*/ ObjectMapper objectMapper, PatientEntityRepository patientEntityRepository) {
    // this.pipeline = pipeline;
    this.objectMapper = objectMapper;
    this.patientEntityRepository = patientEntityRepository;
  }

  @RabbitListener(queues = "${inbound.rabbitmq.queue:citas-evaluaciones.inbound}")
  public void onMessage(Message message) {
    try {
      String body = new String(message.getBody());
      Map<String, Object> parsed = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});

      String routingKey = message.getMessageProperties() != null
        ? message.getMessageProperties().getReceivedRoutingKey()
        : null;

      String eventName = readString(parsed, "event", "event_name", "eventName");
      Map<String, Object> payload = extractPayload(parsed);
      PatientEntity entity = new PatientEntity();
      entity.setId(UUID.fromString(payload.get("pacienteId").toString()));
      entity.setName(payload.get("nombre").toString());
      patientEntityRepository.save(entity);
      // Result result = new ProcessSubscriptionEventCommand(eventName, payload, routingKey).execute(pipeline);
      // if (result.isFailure()) {
      //   log.warn("Inbound subscription event failed: event={} routingKey={} error={}", eventName, routingKey, result.getError().getDescription());
      // }
    } catch (Exception ex) {
      ex.printStackTrace();
      log.error("Inbound subscription message parse failure", ex);
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> extractPayload(Map<String, Object> parsed) {
    Object payload = parsed.get("payload");
    if (payload instanceof Map<?, ?> mapPayload) {
      return (Map<String, Object>) mapPayload;
    }
    return parsed;
  }

  private String readString(Map<String, Object> values, String... keys) {
    for (String key : keys) {
      Object value = values.get(key);
      if (value instanceof String str && !str.isBlank()) {
        return str;
      }
    }
    return null;
  }
}
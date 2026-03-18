package edu.nur.nurtricenter_appointment.infraestructure.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nur.nurtricenter_appointment.core.abstractions.AggregateRoot;
import edu.nur.nurtricenter_appointment.core.abstractions.DomainEvent;
import edu.nur.nurtricenter_appointment.core.abstractions.IUnitOfWork;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox.OutboxEventEntity;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox.OutboxEventMapper;
import edu.nur.nurtricenter_appointment.infraestructure.persistence.outbox.OutboxEventRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class UnitOfWorkJpa implements IUnitOfWork {
	private final EntityManager em;
	private final OutboxEventRepository outboxRepository;
	private final OutboxEventMapper mapper;

	public UnitOfWorkJpa(EntityManager em, OutboxEventRepository outboxRepository, ObjectMapper objectMapper) {
		this.em = em;
		this.outboxRepository = outboxRepository;
		this.mapper = new OutboxEventMapper(objectMapper);
	}

	@Override
	@Async
	@Transactional
	public CompletableFuture<Void> commitAsync(AggregateRoot... aggregates) {
		List<DomainEvent> events = new ArrayList<>();
		if (aggregates != null) {
			for (AggregateRoot aggregate : aggregates) {
				if (aggregate != null) {
					events.addAll(aggregate.getDomainEvents());
				}
			}
		}
		if (!events.isEmpty()) {
			List<OutboxEventEntity> outbox = new ArrayList<>();
			for (DomainEvent event : events) {
				outbox.add(mapper.toEntity(event));
			}
			outboxRepository.saveAll(outbox);
		}
		this.em.flush();
		if (aggregates != null) {
			for (AggregateRoot aggregate : aggregates) {
				if (aggregate != null) {
					aggregate.clearDomainEvents();
				}
			}
		}
		return CompletableFuture.completedFuture(null);
	}
}

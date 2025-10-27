package edu.nur.nurtricenter_appointment.core.abstractions;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IRepository<TEntity extends AggregateRoot> {

    CompletableFuture<TEntity> getByIdAsync(UUID id, boolean readOnly);

    CompletableFuture<Void> addAsync(TEntity entity);
}
package edu.nur.nurtricenter_appointment.core.abstractions;

import java.util.concurrent.CompletableFuture;

public interface IUnitOfWork {
    CompletableFuture<Void> commitAsync();
}
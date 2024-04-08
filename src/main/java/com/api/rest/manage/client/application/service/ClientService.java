package com.api.rest.manage.client.application.service;

import com.api.rest.manage.client.domain.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {
    public Flux<Client> findAll();
    public Mono<Client> findById(String id);
    public Mono<Client> save(Client client);
    public Mono<Void> delete(String id);
}
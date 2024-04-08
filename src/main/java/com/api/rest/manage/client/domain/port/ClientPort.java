package com.api.rest.manage.client.domain.port;

import com.api.rest.manage.client.domain.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientPort {
    Flux<Client> findAll();
    Mono<Client> findById(String id);
    Mono<Client> save(Client client);
    Mono<Void> deleteById(String id);
}
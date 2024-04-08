package com.api.rest.manage.client.application.service;

import com.api.rest.manage.client.domain.model.Client;
import com.api.rest.manage.client.domain.port.ClientPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ClientServiceImpl implements ClientService {
    private final ClientPort clientPort;

    public ClientServiceImpl(ClientPort clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public Flux<Client> findAll() {
        return clientPort.findAll();
    }

    @Override
    public Mono<Client> findById(String id) {
        return clientPort.findById(id);
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientPort.save(client);
    }

    @Override
    public Mono<Void> delete(String id) {
        return clientPort.deleteById(id);
    }
}
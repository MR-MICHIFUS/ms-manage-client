package com.api.rest.manage.client.infrastructure.adapter;

import com.api.rest.manage.client.domain.model.Client;
import com.api.rest.manage.client.domain.port.ClientPort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ClientMongoRepository implements ClientPort {
    private final ClientCrudMongoRepository clientCrudMongoRepository;

    public ClientMongoRepository(ClientCrudMongoRepository clientCrudMongoRepository) {
        this.clientCrudMongoRepository = clientCrudMongoRepository;
    }

    @Override
    public Flux<Client> findAll() {
        return clientCrudMongoRepository.findAll();
    }

    @Override
    public Mono<Client> findById(String id) {
        return clientCrudMongoRepository.findById(id);
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientCrudMongoRepository.save(client);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return clientCrudMongoRepository.deleteById(id);
    }
}
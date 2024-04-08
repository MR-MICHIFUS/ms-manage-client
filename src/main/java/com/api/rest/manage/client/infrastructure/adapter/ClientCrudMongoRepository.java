package com.api.rest.manage.client.infrastructure.adapter;

import com.api.rest.manage.client.domain.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientCrudMongoRepository extends ReactiveMongoRepository<Client, String> {
}
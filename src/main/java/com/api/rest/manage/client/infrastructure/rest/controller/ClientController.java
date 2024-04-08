package com.api.rest.manage.client.infrastructure.rest.controller;

import com.api.rest.manage.client.application.service.ClientService;
import com.api.rest.manage.client.domain.model.Client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Client>>> listClients() {
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(clientService.findAll()));
    }

    @PostMapping
    @CircuitBreaker(name = "clientCB", fallbackMethod = "clientCBFallback")
    public Mono<ResponseEntity<Map<String, Object>>> createClient(@Valid @RequestBody Mono<Client> monoClient) {
        Map<String, Object> response = new HashMap<>();
        return monoClient
                .flatMap(client -> clientService.save(client))
                .map(client -> {
                    response.put("message", "Client created");
                    response.put("client", client);
                    response.put("timestamp", new Date());
                    return ResponseEntity.created(URI.create("/api/client/" + client.getId()))
                            .body(response);
                })
                .onErrorResume(t -> {
                    return Mono.just(t).cast(WebExchangeBindException.class)
                            .flatMap(e -> Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fe -> "El campo: " + fe.getField() + " " + fe.getDefaultMessage())
                            .collectList()
                            .flatMap(list -> {
                                response.put("client", list);
                                response.put("timestamp", new Date());
                                response.put("status", HttpStatus.BAD_REQUEST.value());
                                return Mono.just(ResponseEntity.badRequest().body(response));
                            });
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Client>> getClient(@PathVariable String id) {
        return clientService.findById(id)
                .map(client -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(client))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable String id, @RequestBody Client client) {
        return clientService.findById(id)
                .flatMap(c -> {
                    client.setDocumentType(c.getDocumentType());
                    client.setDocumentNumber(c.getDocumentNumber());
                    client.setName(c.getName());
                    client.setLastName(c.getLastName());
                    client.setEmail(c.getEmail());
                    client.setBrithDate(c.getBrithDate());
                    return clientService.save(client);
                }).map(c -> ResponseEntity.created(URI.create("/api/client/" + c.getId()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(c))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable String id) {
        return clientService.findById(id)
                .flatMap(c -> clientService.delete(c.getId()))
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));

    }

    private Mono<ResponseEntity<Map<String, Object>>> clientCBFallback(Mono<Client> monoClient, Throwable t) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "Service unavailable");
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response));
    }
}
package com.api.rest.manage.client;

import com.api.rest.manage.client.domain.model.Client;
import com.api.rest.manage.client.domain.port.ClientPort;
import com.api.rest.manage.client.infrastructure.rest.controller.ClientController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerTest {
    private final static String BASE_URL = "http://localhost:8080/api/client";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientPort clientPort;

    @Test
    public void listClients() {
        webTestClient.get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus().isOk();
    }

    @ParameterizedTest
    @ValueSource(strings = {"6613565ef1172c6fc1cfba7d"})
    public void getClient(String id) {
        webTestClient.get()
                .uri(BASE_URL + "/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Client.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"6613565ef1172c6fc1cfba8d"})
    public void getClientNotFound(String id) {
        webTestClient.get()
                .uri(BASE_URL + "/" + id)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void createClient() {
        webTestClient.post()
                .uri(BASE_URL)
                .body(Mono.just(Client.builder()
                        .documentType("DNI")
                        .documentNumber("70387337")
                        .name("Roxana")
                        .build()), Client.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @ParameterizedTest
    @ValueSource(strings = {"66138ded57d645795d5239e3"})
    public void updateClient(String id) {
        webTestClient.put()
                .uri(BASE_URL)
                .body(Mono.just(Client.builder()
                        .id(id)
                        .documentType("DNI")
                        .documentNumber("70387337")
                        .name("Roxana")
                        .build()), Client.class)
                .exchange()
                .expectStatus().isOk();
    }

    @ParameterizedTest
    @ValueSource(strings = {"66138ded57d645795d5239e3"})
    public void deleteClient(String id) {
        webTestClient.delete()
                .uri(BASE_URL + "/" + id)
                .exchange()
                .expectStatus().isNoContent();
    }

    @ParameterizedTest
    @ValueSource(strings = {"66138ded57d645795d5239e3"})
    public void deleteClientNotFound(String id) {
        webTestClient.delete()
                .uri(BASE_URL + "/" + id)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void createClientCircuitBreaker() {
        Client client = Client.builder()
                        .documentType("DNI")
                        .documentNumber("70387337")
                        .name("Pedrito")
                        .build();

        Mockito.when(clientPort.save(client))
                .thenReturn(Mono.error(new RuntimeException("Error in CircuitBreaker")));

        StepVerifier.create(clientController.createClient(Mono.just(client)))
                .expectErrorMatches(e -> e instanceof RuntimeException &&
                        e.getMessage().contains("Error in CircuitBreaker"))
                .verify();
    }
}

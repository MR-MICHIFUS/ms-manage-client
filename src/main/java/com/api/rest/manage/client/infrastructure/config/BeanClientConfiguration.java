package com.api.rest.manage.client.infrastructure.config;

import com.api.rest.manage.client.application.service.ClientService;
import com.api.rest.manage.client.application.service.ClientServiceImpl;
import com.api.rest.manage.client.domain.port.ClientPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanClientConfiguration {
    @Bean
    public ClientService clientService(final ClientPort clientPort) {
        return new ClientServiceImpl(clientPort);
    }
}

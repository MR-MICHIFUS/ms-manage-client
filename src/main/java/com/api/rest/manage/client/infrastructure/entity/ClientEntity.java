package com.api.rest.manage.client.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Size(min = 3, max = 3)
    @NotEmpty(message = "El tipo de documento no puede estar vacio")
    private String documentType;
    @Size(min = 8, max = 8)
    @NotEmpty(message = "El numero de documento no puede estar vacio")
    private String documentNumber;
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String name;
    private String lastName;
    @Email(message = "El email no es valido")
    private String email;
    private LocalDateTime brithDate;
    private String photo;
}
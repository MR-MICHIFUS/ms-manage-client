package com.api.rest.manage.client.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Data
@Builder
public class Client {
    private String id;
    private String documentType;
    private String documentNumber;
    private String name;
    private String lastName;
    private String email;
    private LocalDateTime brithDate;
    private String photo;
}
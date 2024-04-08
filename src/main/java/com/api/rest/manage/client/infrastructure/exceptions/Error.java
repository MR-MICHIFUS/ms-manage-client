package com.api.rest.manage.client.infrastructure.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Error {
    private String code;
    private String message;
}

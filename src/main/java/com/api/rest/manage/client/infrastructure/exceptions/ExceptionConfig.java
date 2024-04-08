package com.api.rest.manage.client.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Error> handle(WebClientResponseException e) {
        Error error = Error.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(e.getResponseBodyAsString()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handle(ResourceNotFoundException e) {
        Error error = Error.builder()
                .code(HttpStatus.NOT_FOUND.name())
                .message(e.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handle(Exception e) {
        Error error = Error.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(e.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
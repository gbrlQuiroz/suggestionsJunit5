package com.challenge.suggestions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHelper {

    // exception genérica que recibe el HttpStatus desde el throw new
    @ExceptionHandler(value = { ResponseException.class })
    public ResponseEntity<Object> handleResponseException(ResponseException ex) {
        log.warn("{}", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), ex.getStatus());
    }

    // exception especifica que siempre regresa 404
    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundtException(NotFoundException ex) {
        log.warn("{}", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
}

package com.challenge.suggestions.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        log.error("{}", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), ex.getStatus());
    }

    // genera el listado de que campos presentan errores y regresa un 400 
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }


    // exception específica que siempre regresa 404
    @ExceptionHandler(value = { NotFoundException.class })
    public ResponseEntity<Object> handleNotFoundtException(NotFoundException ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    // exception 500 
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("{}", ex.getMessage());
        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

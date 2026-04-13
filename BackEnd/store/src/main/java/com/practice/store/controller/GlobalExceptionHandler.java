package com.practice.store.controller;

import com.practice.store.exception.CarritoNoEncontradoException;
import com.practice.store.exception.CarritoProductoNoEncontradoException;
import com.practice.store.exception.ErrorResponse;
import com.practice.store.exception.ProductoNoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private ErrorResponse buildError(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse>  handleProductoNoEncontrado(ProductoNoEncontradoException ex){
        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CarritoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse>  handleCarritoNoEncontrado(CarritoNoEncontradoException ex){
        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CarritoProductoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse>  handleProductoProductoNoEncontrado(CarritoProductoNoEncontradoException ex){
        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("errors", errores);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        log.error("Unexpected error occurred: {}",ex);

        return new ResponseEntity<>(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}

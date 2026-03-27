package com.practice.project.controller;

import com.practice.project.exceptions.ErrorResponse;
import com.practice.project.exceptions.PaqueteNotFoundException;
import com.practice.project.exceptions.PedidoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 🧩 Método helper para evitar repetir código
    private ErrorResponse buildError(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
    }

    // 🔴 Pedido no encontrado
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNotFound(PedidoNotFoundException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    // 🔴 Paquete no encontrado
    @ExceptionHandler(PaqueteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaqueteNotFound(PaqueteNotFoundException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    // 🟡 Validaciones (nivel empresa 🔥)
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

    // 🔥 Error genérico (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {

        // 👉 En empresa usarías logger (log.error)
        log.error("Unexpected error occurred: {}",ex);

        return new ResponseEntity<>(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

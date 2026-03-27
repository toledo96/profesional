package com.practice.project.exceptions;

public class PaqueteNotFoundException extends RuntimeException{
    public PaqueteNotFoundException(Long id) {
        super("Paquete con id " + id + " no encontrado");
    }
}

package com.practice.store.exception;

public class CarritoNoEncontradoException extends RuntimeException{
    public CarritoNoEncontradoException(long id) {
        super("Carrito con id:" + id + " no encontrado");
    }
}

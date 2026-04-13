package com.practice.store.exception;

public class CarritoProductoNoEncontradoException extends RuntimeException{
    public CarritoProductoNoEncontradoException(long id) {
        super("CarritoProducto con id:" + id + " no encontrado");
    }
}

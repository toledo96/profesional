package com.practice.store.exception;

public class ProductoNoEncontradoException extends RuntimeException{
    public ProductoNoEncontradoException(long id) {
        super("Producto con id:" + id + " no encontrado");
    }
}

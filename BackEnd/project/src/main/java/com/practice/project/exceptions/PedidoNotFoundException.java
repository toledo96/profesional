package com.practice.project.exceptions;

public class PedidoNotFoundException extends RuntimeException{
    public PedidoNotFoundException(Long id) {
        super("Pedido con id " + id + " no encontrado");
    }
}

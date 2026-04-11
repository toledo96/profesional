package com.practice.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
Entidad intermedia (CarritoProducto):

- En sistemas de e-commerce profesionales, se suele usar una tabla intermedia que además guarda cantidad,
precio en el momento de la compra, y otros metadatos.

- Carrito ↔ CarritoProducto ↔ Producto

- Esto evita problemas y permite manejar cantidades (ej. 3 unidades de un mismo producto en el carrito).

*/

@Entity
@Table(name = "productos-carritos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carritoProductoId;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne
    private Producto producto;

    private Integer cantidad;

    private Double precioUnitario; // precio en el momento de agregar

}

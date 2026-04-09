package com.practice.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCarrito;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoProducto> productos = new ArrayList<>();


}

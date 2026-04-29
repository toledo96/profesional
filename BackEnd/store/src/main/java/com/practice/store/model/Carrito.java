package com.practice.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    private Long idCarrito;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    @Size(min = 1, message = "Deben existir productos")
    private List<CarritoProducto> productos = new ArrayList<>();


}

package com.practice.store.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducto;

    private String nombreProducto;

    private Double precio;

    private Integer cantidadStock;

    public int enStock(){
        return cantidadStock > 0 ? cantidadStock : 0;
    }

}

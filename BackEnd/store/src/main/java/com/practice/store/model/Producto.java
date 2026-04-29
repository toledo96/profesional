package com.practice.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private Long idProducto;

    @NotBlank
    private String nombreProducto;

    @NotNull
    private Double precio;

    @NotNull
    private Integer cantidadStock;

    public boolean enStock(){
        return cantidadStock > 0 ? true : false;
    }

}

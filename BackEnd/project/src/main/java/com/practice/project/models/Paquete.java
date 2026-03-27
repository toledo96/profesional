package com.practice.project.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paquetes")
public class Paquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paquete_id;

    @NotBlank(message = "Se debe asignar el tamanio")
    private String tamanio;

    @ManyToOne
    @JoinColumn(name = "id") // clave foránea en la tabla Paquete
    @JsonBackReference
    private Pedido pedido;


}

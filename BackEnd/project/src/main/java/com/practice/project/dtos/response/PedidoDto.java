package com.practice.project.dtos.response;

import com.practice.project.models.Paquete;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoDto {

    private Long id;
    private List<Paquete> paquetes = new ArrayList<>();
}

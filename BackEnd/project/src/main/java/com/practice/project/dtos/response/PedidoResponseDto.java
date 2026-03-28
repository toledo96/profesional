package com.practice.project.dtos.response;

import com.practice.project.models.Paquete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PedidoResponseDto {

    private Long id;
    private List<Paquete> paquetes = new ArrayList<>();
}

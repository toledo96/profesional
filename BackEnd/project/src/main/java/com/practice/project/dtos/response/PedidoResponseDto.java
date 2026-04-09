package com.practice.project.dtos.response;

import com.practice.project.models.Paquete;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PedidoResponseDto {

    private Long id;
    private List<Paquete> paquetes = new ArrayList<>();
}

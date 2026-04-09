package com.practice.project.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDto {
    @NotBlank(message = "Se debe asignar el proveedor")
    private String proveedor;


    private List<PaqueteRequestDto> paquetes;

}

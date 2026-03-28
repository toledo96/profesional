package com.practice.project.dtos.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDto {
    @NotBlank(message = "Se debe asignar el proveedor")
    private String proveedor;


    private List<PaqueteRequestDto> paquetes;

}

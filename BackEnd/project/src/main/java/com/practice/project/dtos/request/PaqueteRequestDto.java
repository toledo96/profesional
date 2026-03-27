package com.practice.project.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaqueteRequestDto {
    @NotBlank(message = "Se debe asignar el tamanio")
    private String tamanio;

    private Long pedidoId; // 🔥 SOLO el ID
}

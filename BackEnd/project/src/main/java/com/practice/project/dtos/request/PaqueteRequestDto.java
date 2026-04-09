package com.practice.project.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaqueteRequestDto {
    @NotBlank(message = "Se debe asignar el tamanio")
    private String tamanio;

    private Long pedidoId; // 🔥 SOLO el ID
}

package com.practice.project.mappers;

import com.practice.project.dtos.request.PaqueteRequestDto;
import com.practice.project.dtos.response.PaqueteResponseDto;
import com.practice.project.models.Paquete;
import com.practice.project.models.Pedido;


public class PaqueteMapper {

    // 📤 Entity → Response DTO
    public static PaqueteResponseDto toDto(Paquete paquete) {
        PaqueteResponseDto dto = new PaqueteResponseDto();
        dto.setPaquete_id(paquete.getPaquete_id());
        dto.setTamanio(paquete.getTamanio());
        return dto;
    }

    // 📥 Request DTO → Entity
    public static Paquete toEntity(PaqueteRequestDto dto, Pedido pedido) {
        Paquete paquete = new Paquete();
        paquete.setTamanio(dto.getTamanio());
        paquete.setPedido(pedido); // 🔥 relación se setea aquí
        return paquete;
    }

}

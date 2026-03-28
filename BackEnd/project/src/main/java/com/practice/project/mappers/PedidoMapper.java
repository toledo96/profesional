package com.practice.project.mappers;

import com.practice.project.dtos.response.PedidoResponseDto;
import com.practice.project.models.Pedido;

public class PedidoMapper {

    public static PedidoResponseDto toDto(Pedido pedido){
        PedidoResponseDto pedidoDto = new PedidoResponseDto();
        pedidoDto.setId(pedido.getId());
        pedidoDto.setPaquetes(pedido.getPaquetes());
        return pedidoDto;
    }

    public static Pedido toEntity(PedidoResponseDto pedidoDto){
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDto.getId());
        pedido.setPaquetes(pedidoDto.getPaquetes());
        return pedido;
    }

}

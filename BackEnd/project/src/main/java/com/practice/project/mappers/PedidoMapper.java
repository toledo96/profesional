package com.practice.project.mappers;

import com.practice.project.dtos.response.PedidoDto;
import com.practice.project.models.Pedido;

public class PedidoMapper {

    public static PedidoDto toDto(Pedido pedido){
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(pedido.getId());
        pedidoDto.setPaquetes(pedido.getPaquetes());
        return pedidoDto;
    }

    public static Pedido toEntity(PedidoDto pedidoDto){
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDto.getId());
        pedido.setPaquetes(pedidoDto.getPaquetes());
        return pedido;
    }

}

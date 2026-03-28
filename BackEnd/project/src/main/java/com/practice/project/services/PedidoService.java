package com.practice.project.services;

import com.practice.project.dtos.request.PedidoRequestDto;
import com.practice.project.dtos.response.PedidoResponseDto;
import com.practice.project.models.Pedido;

import java.util.List;

public interface PedidoService {

    public PedidoResponseDto createPedido(PedidoRequestDto pedido);

    public void deletePedido(Long pedido_id);

    public PedidoResponseDto getPedido(Long pedido_id);

    public PedidoResponseDto updatePedido(Long id, PedidoRequestDto pedido);

    public List<PedidoResponseDto> getAll();

}

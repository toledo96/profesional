package com.practice.project.services;

import com.practice.project.dtos.response.PedidoDto;
import com.practice.project.models.Pedido;

import java.util.List;

public interface PedidoService {

    public PedidoDto createPedido(Pedido pedido);

    public void deletePedido(Long pedido_id);

    public PedidoDto getPedido(Long pedido_id);

    public PedidoDto updatePedido(Long id,Pedido pedido);

    public List<PedidoDto> getAll();

}

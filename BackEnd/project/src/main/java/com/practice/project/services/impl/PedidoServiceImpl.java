package com.practice.project.services.impl;

import com.practice.project.dtos.response.PedidoDto;
import com.practice.project.exceptions.PedidoNotFoundException;
import com.practice.project.mappers.PedidoMapper;
import com.practice.project.models.Pedido;
import com.practice.project.repositories.PedidoRepository;
import com.practice.project.services.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Override
    public PedidoDto createPedido(Pedido pedido) {
        Pedido p = pedidoRepository.save(pedido);
        p.setPaquetes(
                p.getPaquetes().stream()
                        .peek(paquete -> paquete.setPedido(p))
                        .collect(Collectors.toList())
        );

        return PedidoMapper.toDto(p);
    }

    @Override
    public void deletePedido(Long pedido_id) {
        pedidoRepository.deleteById(pedido_id);
    }

    @Override
    public PedidoDto getPedido(Long pedido_id) {
        Pedido pedido = pedidoRepository.findById(pedido_id)
                .orElseThrow(() -> new PedidoNotFoundException(pedido_id));

        return PedidoMapper.toDto(pedido);
    }

    @Override
    public PedidoDto updatePedido(Long id ,Pedido pedido) {
        Pedido p = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(pedido.getId()));
        p.setPaquetes(pedido.getPaquetes());
        Pedido actualizado = pedidoRepository.save(p);
        return PedidoMapper.toDto(actualizado);
    }

    @Override
    public List<PedidoDto> getAll() {
        return pedidoRepository.findAll().stream().map(PedidoMapper::toDto)
                .collect(Collectors.toList());
    }
}

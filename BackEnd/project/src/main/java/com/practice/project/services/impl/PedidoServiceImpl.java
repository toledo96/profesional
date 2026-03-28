package com.practice.project.services.impl;

import com.practice.project.dtos.request.PedidoRequestDto;
import com.practice.project.dtos.response.PedidoResponseDto;
import com.practice.project.exceptions.PedidoNotFoundException;
import com.practice.project.mappers.PedidoMapper;
import com.practice.project.models.Paquete;
import com.practice.project.models.Pedido;
import com.practice.project.repositories.PedidoRepository;
import com.practice.project.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Override
    public PedidoResponseDto createPedido(PedidoRequestDto pedidoDto) {
       Pedido pedido =  new Pedido();
       pedido.setProveedor(pedidoDto.getProveedor());

       List<Paquete> paquetes = pedidoDto.getPaquetes().stream()
               .map(p -> {
                   Paquete paquete = new Paquete();
                   paquete.setTamanio(p.getTamanio());
                   paquete.setPedido(pedido);
                   return paquete;
               }).toList();

       pedido.setPaquetes(paquetes);
       Pedido saved = pedidoRepository.save(pedido);
       return PedidoMapper.toDto(saved);
    }

    @Override
    public void deletePedido(Long pedido_id) {
        Pedido pedido = pedidoRepository.findById(pedido_id)
                .orElseThrow(() -> new PedidoNotFoundException(pedido_id));
        pedidoRepository.deleteById(pedido_id);
    }

    @Override
    public PedidoResponseDto getPedido(Long pedido_id) {
        Pedido pedido = pedidoRepository.findById(pedido_id)
                .orElseThrow(() -> new PedidoNotFoundException(pedido_id));

        return PedidoMapper.toDto(pedido);
    }

    @Override
    public PedidoResponseDto updatePedido(Long id , PedidoRequestDto pedidoDto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        if (pedidoDto.getPaquetes() != null) {
            List<Paquete> paquetes = pedidoDto.getPaquetes().stream()
                    .map(p -> {
                        Paquete paquete = new Paquete();
                        paquete.setTamanio(p.getTamanio());
                        paquete.setPedido(pedido);
                        return paquete;
                    })
                    .toList();

            pedido.setPaquetes(paquetes);
        }
        Pedido saved = pedidoRepository.save(pedido);
        return PedidoMapper.toDto(saved);
    }

    @Override
    public List<PedidoResponseDto> getAll() {
        return pedidoRepository.findAll().stream().map(PedidoMapper::toDto)
                .collect(Collectors.toList());
    }
}

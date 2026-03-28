package com.practice.project.services.impl;

import com.practice.project.dtos.request.PaqueteRequestDto;
import com.practice.project.dtos.response.PaqueteResponseDto;
import com.practice.project.exceptions.PaqueteNotFoundException;
import com.practice.project.exceptions.PedidoNotFoundException;
import com.practice.project.mappers.PaqueteMapper;
import com.practice.project.models.Paquete;
import com.practice.project.models.Pedido;
import com.practice.project.repositories.PaqueteRepository;
import com.practice.project.repositories.PedidoRepository;
import com.practice.project.services.PaqueteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaqueteServiceImpl implements PaqueteService {

    private final PaqueteRepository paqueteRepository;
    private final PedidoRepository pedidoRepository;

    @Override
    public PaqueteResponseDto createPaquete(PaqueteRequestDto dto) {
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new PedidoNotFoundException(dto.getPedidoId()));

        Paquete paquete = PaqueteMapper.toEntity(dto, pedido);

        Paquete saved = paqueteRepository.save(paquete);

        return PaqueteMapper.toDto(saved);
    }

    @Override
    public PaqueteResponseDto updatePaquete(Long id, PaqueteRequestDto paqueteDto) {
        Paquete paqueteEncontrado = paqueteRepository.findById(id)
                .orElseThrow(() -> new PaqueteNotFoundException(id));
        paqueteEncontrado.setTamanio(paqueteDto.getTamanio());

        if(paqueteDto.getPedidoId() != null){
            Pedido pedido = pedidoRepository.findById(paqueteDto.getPedidoId())
                    .orElseThrow(() -> new PedidoNotFoundException(paqueteDto.getPedidoId()));
            paqueteEncontrado.setPedido(pedido);
        }
        Paquete actualizado = paqueteRepository.save(paqueteEncontrado);
        return PaqueteMapper.toDto(actualizado);
    }

    @Override
    public PaqueteResponseDto getPaquete(Long paquete_id) {
        Paquete paquete1 = paqueteRepository.findById(paquete_id)
                .orElseThrow(() -> new PaqueteNotFoundException(paquete_id));
        return PaqueteMapper.toDto(paquete1);
    }

    @Override
    public void deletePaquete(Long paquete_id) {
        Paquete paquete = paqueteRepository.findById(paquete_id)
                .orElseThrow(() -> new PaqueteNotFoundException(paquete_id));

        paqueteRepository.delete(paquete);
    }
}

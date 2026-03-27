package com.practice.project.services.impl;

import com.practice.project.dtos.response.PaqueteDto;
import com.practice.project.exceptions.PaqueteNotFoundException;
import com.practice.project.mappers.PaqueteMapper;
import com.practice.project.models.Paquete;
import com.practice.project.repositories.PaqueteRepository;
import com.practice.project.services.PaqueteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaqueteServiceImpl implements PaqueteService {

    private final PaqueteRepository paqueteRepository;

    @Override
    public PaqueteDto createPaquete(Paquete paquete) {
        Paquete paquete_guardado = paqueteRepository.save(paquete);
        return PaqueteMapper.toDto(paquete_guardado);
    }

    @Override
    public PaqueteDto updatePaquete(Long id,Paquete paquete) {
        Paquete paquete_encontrado = paqueteRepository.findById(id)
                .orElseThrow(() -> new PaqueteNotFoundException(paquete.getPaquete_id()));
        paquete_encontrado.setTamanio(paquete.getTamanio());
        Paquete actualizado = paqueteRepository.save(paquete_encontrado);
        return PaqueteMapper.toDto(actualizado);
    }

    @Override
    public PaqueteDto getPaquete(Long paquete_id) {
        Paquete paquete1 = paqueteRepository.findById(paquete_id)
                .orElseThrow(() -> new PaqueteNotFoundException(paquete_id));
        return PaqueteMapper.toDto(paquete1);
    }

    @Override
    public void deletePaquete(Long paquete_id) {
        paqueteRepository.deleteById(paquete_id);
    }
}

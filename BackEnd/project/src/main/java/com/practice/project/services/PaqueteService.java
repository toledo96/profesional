package com.practice.project.services;

import com.practice.project.dtos.request.PaqueteRequestDto;
import com.practice.project.dtos.response.PaqueteResponseDto;
import com.practice.project.models.Paquete;

public interface PaqueteService {

    public PaqueteResponseDto createPaquete(PaqueteRequestDto paquete);

    public PaqueteResponseDto updatePaquete(Long id, PaqueteRequestDto paquete);

    public PaqueteResponseDto getPaquete(Long paquete_id);

    public void deletePaquete(Long paquete_id);

}

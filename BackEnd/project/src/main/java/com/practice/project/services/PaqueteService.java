package com.practice.project.services;

import com.practice.project.dtos.response.PaqueteDto;
import com.practice.project.models.Paquete;

public interface PaqueteService {

    public PaqueteDto createPaquete(Paquete paquete);

    public PaqueteDto updatePaquete(Long id,Paquete paquete);

    public PaqueteDto getPaquete(Long paquete_id);

    public void deletePaquete(Long paquete_id);

}

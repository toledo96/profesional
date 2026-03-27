package com.practice.project.mappers;

import com.practice.project.dtos.response.PaqueteDto;
import com.practice.project.models.Paquete;


public class PaqueteMapper {

    public static PaqueteDto toDto(Paquete paquete){
        PaqueteDto paqueteDto = new PaqueteDto();
        paqueteDto.setPaquete_id(paquete.getPaquete_id());
        paqueteDto.setTamanio(paquete.getTamanio());
        return paqueteDto;
    }

    public static Paquete toEntity(PaqueteDto paqueteDto){
        Paquete paquete = new Paquete();
        paquete.setPaquete_id(paqueteDto.getPaquete_id());
        paquete.setTamanio(paquete.getTamanio());
        return paquete;
    }

}

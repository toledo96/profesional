package com.practice.project.services.impl;

import com.practice.project.dtos.request.PaqueteRequestDto;
import com.practice.project.dtos.response.PaqueteResponseDto;
import com.practice.project.models.Paquete;
import com.practice.project.models.Pedido;
import com.practice.project.repositories.PaqueteRepository;
import com.practice.project.repositories.PedidoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaqueteServiceImplTest {

    @Mock
    private PaqueteRepository paqueteRepository; // Simulas dependencias (repositorios, clientes, etc.)

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PaqueteServiceImpl paqueteService; // Mockito inyecta los mocks dentro del servicio

    PaqueteRequestDto paqueteDto = new PaqueteRequestDto();

    Pedido pedido = new Pedido();


    @BeforeEach
    public void setup(){
        paqueteDto.setPedidoId(1L);
        paqueteDto.setTamanio("grande");

        pedido.setId(1L);
    }

    @Test
    void createPaquete() {
        Optional<Pedido> pedidoOptional = Optional.of(pedido);
        Mockito.when(pedidoRepository.findById(Mockito.anyLong())).thenReturn(pedidoOptional);

        Paquete paquete = Paquete.builder()
                .paquete_id(1L)
                .pedido(pedido)
                .tamanio("grande")
                .build();

        Mockito.when(paqueteRepository.save(Mockito.any())).thenReturn(paquete);

        //Accion
        PaqueteResponseDto response = paqueteService.createPaquete(paqueteDto);

        //Assert
        assertNotNull(response);

        //Verify
        Mockito.verify(pedidoRepository).findById(Mockito.anyLong());
        Mockito.verify(paqueteRepository).save(Mockito.any());
    }

    @Test
    void updatePaquete() {
        Paquete paquete = Paquete.builder()
                .paquete_id(1L)
                .pedido(pedido)
                .tamanio(paqueteDto.getTamanio())
                .build();
        Optional<Paquete> optionalPaquete = Optional.of(paquete);
        Mockito.when(paqueteRepository.findById(Mockito.anyLong())).thenReturn(optionalPaquete);

        Optional<Pedido> pedidoOptional = Optional.of(pedido);

        Mockito.when(pedidoRepository.findById(Mockito.anyLong())).thenReturn(pedidoOptional);

        Mockito.when(paqueteRepository.save(Mockito.any())).thenReturn(paquete);

        PaqueteRequestDto paqueteRequestDto = PaqueteRequestDto.builder()
                .pedidoId(pedidoOptional.get().getId())
                .tamanio("mediano")
                .build();

        //Action
        PaqueteResponseDto paqueteActualizado = paqueteService.updatePaquete(1L,paqueteRequestDto);

        //assert
        assertNotNull(paqueteActualizado);
        assertEquals(paqueteRequestDto.getTamanio(), paqueteActualizado.getTamanio());

        //Verify
        Mockito.verify(pedidoRepository).findById(Mockito.anyLong());
        Mockito.verify(paqueteRepository).findById(Mockito.anyLong());
        Mockito.verify(paqueteRepository).save(Mockito.any());

    }

    @Test
    void getPaquete() {
        Paquete paquete = Paquete.builder()
                .paquete_id(1L)
                .tamanio("mediano")
                .pedido(pedido)
                .build();
        Optional<Paquete> paqueteOptional = Optional.of(paquete);
        Mockito.when(paqueteRepository.findById(Mockito.anyLong())).thenReturn(paqueteOptional);

        //Action
        PaqueteResponseDto paqueteEncontrado = paqueteService.getPaquete(paquete.getPaquete_id());

        //Assert
        assertNotNull(paqueteEncontrado);
        assertEquals(paquete.getTamanio(),paqueteEncontrado.getTamanio());

        //Verify
        Mockito.verify(paqueteRepository).findById(Mockito.anyLong());
    }

    @Test
    void deletePaquete() {
        Paquete paquete = Paquete.builder()
                .paquete_id(1L)
                .tamanio("mediano")
                .pedido(pedido)
                .build();
        Optional<Paquete> paqueteOptional = Optional.of(paquete);
        Mockito.when(paqueteRepository.findById(Mockito.anyLong())).thenReturn(paqueteOptional);


        //Action
        paqueteService.deletePaquete(1L);

        // Verify
        Mockito.verify(paqueteRepository).findById(Mockito.anyLong());
        Mockito.verify(paqueteRepository).delete(Mockito.any());
    }
}
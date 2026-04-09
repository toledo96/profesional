package com.practice.project.services.impl;

import com.practice.project.dtos.request.PaqueteRequestDto;
import com.practice.project.dtos.request.PedidoRequestDto;
import com.practice.project.dtos.response.PedidoResponseDto;
import com.practice.project.models.Paquete;
import com.practice.project.models.Pedido;
import com.practice.project.repositories.PedidoRepository;
import com.practice.project.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    Pedido pedido = new Pedido();

    List<Paquete> paquetes = new ArrayList<>();


    @BeforeEach
    public void setup(){
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProveedor("Amazon");

        paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1L, "grande", pedido));
        paquetes.add(new Paquete(2L, "mediano", pedido));

        pedido.setPaquetes(paquetes);
    }


    @Test
    void createPedido() {
        List<PaqueteRequestDto> paqueteRequestDto = new ArrayList<>();
        paqueteRequestDto.add(new PaqueteRequestDto("grande",1L));

        PedidoRequestDto pedidoRequestDto = PedidoRequestDto.builder()
                .paquetes(paqueteRequestDto)
                .proveedor("Amazon")
                .build();


        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedido);

        //act
        PedidoResponseDto pedidoResponseDto = pedidoService.createPedido(pedidoRequestDto);

        //Assert
        assertNotNull(pedidoResponseDto);
        assertEquals(1L, pedidoResponseDto.getId());

        Mockito.verify(pedidoRepository).save(Mockito.any());

    }

    @Test
    void deletePedido() {
        Optional pedidoOptional = Optional.of(pedido);
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(pedidoOptional);

        pedidoService.deletePedido(1L);

        Mockito.verify(pedidoRepository).findById(Mockito.anyLong());
        Mockito.verify(pedidoRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void getPedido() {
        Optional pedidoOptional = Optional.of(pedido);
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(pedidoOptional);

        pedidoService.getPedido(1L);

        Mockito.verify(pedidoRepository).findById(Mockito.anyLong());
    }

    @Test
    void updatePedido() {
        Optional pedidoOptional = Optional.of(pedido);
        Mockito.when(pedidoRepository.findById(Mockito.anyLong()))
                .thenReturn(pedidoOptional);

        List<PaqueteRequestDto> paqueteRequestDto = new ArrayList<>();
        paqueteRequestDto.add(new PaqueteRequestDto("grande",1L));

        PedidoRequestDto pedidoRequestDto = PedidoRequestDto.builder()
                .paquetes(paqueteRequestDto)
                .proveedor("Amazon")
                .build();


        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedido);

        //act
        PedidoResponseDto pedidoResponseDto = pedidoService.updatePedido(1L,pedidoRequestDto);

        //Assert
        assertNotNull(pedidoResponseDto);
        assertEquals(1L, pedidoResponseDto.getId());

        Mockito.verify(pedidoRepository).save(Mockito.any());
    }

    @Test
    void getAll() {

        List<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new Pedido(1L,"mediano",paquetes));

        Mockito.when(pedidoRepository.findAll()).thenReturn(pedidos);

        pedidoService.getAll();

        Mockito.verify(pedidoRepository).findAll();

    }
}
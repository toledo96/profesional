package com.practice.project.controller;

import com.practice.project.dtos.request.PedidoRequestDto;
import com.practice.project.dtos.response.PedidoResponseDto;
import com.practice.project.models.Paquete;
import com.practice.project.models.Pedido;
import com.practice.project.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @MockitoBean
    private PedidoService pedidoService;

    @Autowired
    private MockMvc mockMvc;

    Pedido pedido = new Pedido();

    List<Paquete> paquetes = new ArrayList<>();

    @BeforeEach
    public void setup(){
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProveedor("Amazon");

        paquetes = new ArrayList<>();
        paquetes.add(new Paquete(1L, "Grande", pedido));
        paquetes.add(new Paquete(2L, "Mediano", pedido));
        paquetes.add(new Paquete(3L, "Pequeño", pedido));

        pedido.setPaquetes(paquetes);
    }


    @Test
    void createPedido() throws Exception{

        String requestJson = """
            {
              "proveedor": "Proveedor ACME",
              "paquetes": [
                {
                  "tamanio": "Grande"
                },
                {
                  "tamanio": "Mediano"
                },
                {
                  "tamanio": "Pequeño"
                }
              ]
            }
        """;

        String responseJson = """
            {
                "id": 1,
                "paquetes": [
                    {
                        "paquete_id": 1,
                        "tamanio": "Grande"
                    },
                    {
                        "paquete_id": 2,
                        "tamanio": "Mediano"
                    },
                    {
                        "paquete_id": 3,
                        "tamanio": "Pequeño"
                    }
                ]
            }
        """;

        Mockito.when(pedidoService.createPedido(Mockito.any()))
                .thenReturn(new PedidoResponseDto(1L,paquetes));

        mockMvc.perform(post("/api/pedido")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.paquetes.length()").value(3))
                .andExpect(jsonPath("$.paquetes[1].tamanio").value("Mediano"))
                .andExpect(content().json(responseJson));

        Mockito.verify(pedidoService).createPedido(Mockito.any());

    }

    @Test
    void getPedido() throws Exception{
        PedidoResponseDto pedidoResponseDto = new PedidoResponseDto(1L,paquetes);
        Mockito.when(pedidoService.getPedido(Mockito.any())).
                thenReturn(pedidoResponseDto);

        mockMvc.perform(get("/api/pedido/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        Mockito.verify(pedidoService).getPedido(Mockito.anyLong());

    }

    @Test
    void getAllPedidos() throws Exception{
        List<PedidoResponseDto> pedidoResponseDtos = new ArrayList<>();
        pedidoResponseDtos.add(new PedidoResponseDto(1L,paquetes));
        pedidoResponseDtos.add(new PedidoResponseDto(2L,paquetes));
        Mockito.when(pedidoService.getAll()).thenReturn(pedidoResponseDtos);

        mockMvc.perform(get("/api/pedido")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].paquetes.length()").value(3));

        Mockito.verify(pedidoService).getAll();
    }

    @Test
    void updatePedido() throws Exception{

        String requestJson = """
            {
              "id": 1,
              "proveedor": "Proveedor ACME",
               "paquetes": [
                    {
                        "paquete_id": 1,
                        "tamanio": "Grande"
                    },
                    {
                        "paquete_id": 2,
                        "tamanio": "Mediano"
                    },
                    {
                        "paquete_id": 3,
                        "tamanio": "Pequeño"
                    }
                ]
            }
        """;

        String updatedJson = """
            {
              "id": 1,
               "paquetes": [
                    {
                        "paquete_id": 1,
                        "tamanio": "Grande"
                    },
                    {
                        "paquete_id": 2,
                        "tamanio": "Mediano"
                    },
                    {
                        "paquete_id": 3,
                        "tamanio": "Mediano"
                    }
                ]
            }
        """;

        paquetes.get(2).setTamanio("Mediano");

        Mockito.when(pedidoService.updatePedido(Mockito.anyLong(),Mockito.any()))
                .thenReturn(new PedidoResponseDto(1L,paquetes));

        mockMvc.perform(put("/api/pedido/1")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedJson));

        Mockito.verify(pedidoService)
                .updatePedido(Mockito.eq(1L), Mockito.any(PedidoRequestDto.class));

    }

    @Test
    void deletePedido() throws Exception{
        mockMvc.perform(delete("/api/pedido/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
        Mockito.verify(pedidoService).deletePedido(1L);

    }
}
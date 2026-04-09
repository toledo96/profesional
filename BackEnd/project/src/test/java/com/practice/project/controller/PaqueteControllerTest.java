package com.practice.project.controller;

import com.practice.project.dtos.response.PaqueteResponseDto;
import com.practice.project.services.PaqueteService;
import com.practice.project.services.impl.PaqueteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(PaqueteController.class)
class PaqueteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaqueteService paqueteService;



    @Test
    void getById() throws Exception{
        PaqueteResponseDto paqueteResponseDto = PaqueteResponseDto.builder()
                .paquete_id(1L)
                .tamanio("grande")
                .build();
        Mockito.when(paqueteService.getPaquete(Mockito.anyLong())).thenReturn(paqueteResponseDto);

        mockMvc.perform(get("/api/paquete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tamanio").value("grande"));
    }

    @Test
    void create() throws Exception{
        String requestJson = """
                {
                    "pedidoId": 1,
                    "tamanio": "grande"
                }
                """;

        String responseJson = """
                {
                    "paquete_id": 1,
                    "tamanio": "grande"
                }
                """;

        Mockito.when(paqueteService.createPaquete(Mockito.any()))
                .thenReturn(new PaqueteResponseDto(1L,"grande"));

        mockMvc.perform(post("/api/paquete")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));

    }

    @Test
    void update() throws Exception {
        String createdJson = """
                {
                    "paquete_id": 1,
                    "tamanio": "grande"
                }
                """;

        String updatedJson = """
                {
                    "paquete_id": 1,
                    "tamanio": "mediano"
                }
                """;

        Mockito.when(paqueteService.updatePaquete(Mockito.anyLong(),Mockito.any()))
                .thenReturn(new PaqueteResponseDto(1L,"mediano"));

        mockMvc.perform(put("/api/paquete/1")
                .contentType("application/json")
                .content(createdJson))
                .andExpect(status().isOk())
                .andExpect(content().json(updatedJson));
    }

    @Test
    void deletetest() throws Exception{
        mockMvc.perform(delete("/api/paquete/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());

        Mockito.verify(paqueteService).deletePaquete(1L);
    }
}
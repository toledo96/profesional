package com.practice.store.controller;

import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;


    @Test
    void crearProducto() throws Exception{
        String requestId = UUID.randomUUID().toString();
        String requestJson = """
                    {
                        "nombreProducto": "Laptop ASUS-5",
                        "precio": 1570.00,
                        "cantidadStock": 12
                    }
                """;


        String responseJson = """
                       {
                         "idProducto": 1,
                        "nombreProducto": "Laptop ASUS-5",
                        "precio": 1570.00  
                       }                     
                """;

        Mockito.when(productoService.crearProducto(Mockito.any(),Mockito.anyString()))
                .thenReturn(new ProductoResponseDto(1L,"Laptop ASUS-5",1570.00));

        mockMvc.perform(post("/api/productos")
                .contentType("application/json")
                        .header("X-Request-Id",requestId)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));

    }

    @Test
    void actualizarProducto() {

    }

    @Test
    void obtenerProductos() throws Exception{
        String requestId = UUID.randomUUID().toString();
        List<ProductoResponseDto> productoResponseDtos = new ArrayList<>();
        ProductoResponseDto productoResponseDto = new ProductoResponseDto(1L,"Laptop",1234.55);
        productoResponseDtos.add(productoResponseDto);

        Mockito.when(productoService.obtenerProductos())
                .thenReturn(productoResponseDtos);


        mockMvc.perform(get("/api/productos")
                        .contentType("application/json")
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void obtenerProducto() {
    }

    @Test
    void eliminarProducto() {
    }
}
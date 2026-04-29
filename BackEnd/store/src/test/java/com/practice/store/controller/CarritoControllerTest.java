package com.practice.store.controller;

import com.practice.store.service.CarritoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(CarritoController.class)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService carritoService;


    @Test
    void crearCarrito() {
        
    }

    @Test
    void actualizarCarrito() {
    }

    @Test
    void obtenerCarritos() {
    }

    @Test
    void obtenerCarrito() {
    }

    @Test
    void eliminarCarrito() {
    }
}
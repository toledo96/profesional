package com.practice.store.controller;

import com.practice.store.dto.request.ProductoRequestDto;
import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;


    // Crear producto con idempotencia
    @PostMapping
    public ResponseEntity<ProductoResponseDto> crearProducto(
            @RequestBody ProductoRequestDto productoRequestDto,
            @RequestHeader("X-Request-Id") String requestId) {

        ProductoResponseDto response = productoService.crearProducto(productoRequestDto, requestId);
        return ResponseEntity.ok(response);
    }

    // Actualizar producto
    @PutMapping("/{productoId}")
    public ResponseEntity<ProductoResponseDto> actualizarProducto(
            @PathVariable Long productoId,
            @RequestBody ProductoRequestDto productoRequestDto) {

        ProductoResponseDto response = productoService.actualizarProducto(productoRequestDto, productoId);
        return ResponseEntity.ok(response);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> obtenerProductos() {
        List<ProductoResponseDto> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    // Obtener producto por ID
    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoResponseDto> obtenerProducto(@PathVariable Long productoId) {
        ProductoResponseDto response = productoService.obtenerProducto(productoId);
        return ResponseEntity.ok(response);
    }

    // Eliminar producto
    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long productoId) {
        productoService.eliminarProducto(productoId);
        return ResponseEntity.noContent().build();
    }



}

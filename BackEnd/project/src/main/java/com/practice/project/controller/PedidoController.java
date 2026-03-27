package com.practice.project.controller;

import com.practice.project.dtos.response.PedidoDto;
import com.practice.project.models.Pedido;
import com.practice.project.services.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // Crear un pedido
    @PostMapping
    public ResponseEntity<PedidoDto> createPedido(@Valid @RequestBody Pedido pedido) {
        PedidoDto nuevoPedido = pedidoService.createPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    // Obtener un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> getPedido(@PathVariable Long id) {
        PedidoDto pedido = pedidoService.getPedido(id);
        return ResponseEntity.ok(pedido);
    }

    // Obtener todos los pedidos
    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedidos() {
        List<PedidoDto> pedidos = pedidoService.getAll();
        return ResponseEntity.ok(pedidos);
    }

    // Actualizar un pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDto> updatePedido(@PathVariable Long id, @Valid @RequestBody Pedido pedido) {
        pedido.setId(id); // aseguramos que el ID coincida
        PedidoDto actualizado = pedidoService.updatePedido(id,pedido);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}

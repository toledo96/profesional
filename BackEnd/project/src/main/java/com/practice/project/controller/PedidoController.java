package com.practice.project.controller;

import com.practice.project.dtos.request.PedidoRequestDto;
import com.practice.project.dtos.response.PedidoResponseDto;
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

    @PostMapping
    public ResponseEntity<PedidoResponseDto> createPedido(@Valid @RequestBody PedidoRequestDto pedidoDto) {
        PedidoResponseDto nuevoPedido = pedidoService.createPedido(pedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> getPedido(@PathVariable Long id) {
        PedidoResponseDto pedido = pedidoService.getPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAllPedidos() {
        List<PedidoResponseDto> pedidos = pedidoService.getAll();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoRequestDto pedidoDto) {
        PedidoResponseDto actualizado = pedidoService.updatePedido(id,pedidoDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

}

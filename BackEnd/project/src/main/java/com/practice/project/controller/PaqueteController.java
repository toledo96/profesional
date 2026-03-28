package com.practice.project.controller;

import com.practice.project.dtos.request.PaqueteRequestDto;
import com.practice.project.dtos.response.PaqueteResponseDto;
import com.practice.project.models.Paquete;
import com.practice.project.services.PaqueteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paquete")
@AllArgsConstructor
public class PaqueteController {

    private final PaqueteService paqueteService;

    @GetMapping("/{id}")
    public ResponseEntity<PaqueteResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paqueteService.getPaquete(id));
    }

    @PostMapping
    public ResponseEntity<PaqueteResponseDto> create(@Valid @RequestBody PaqueteRequestDto paqueteDto) {
        PaqueteResponseDto paquete1 = paqueteService.createPaquete(paqueteDto);
        return ResponseEntity.status(201).body(paquete1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaqueteResponseDto> update(@PathVariable Long id, @Valid @RequestBody PaqueteRequestDto paqueteDto) {
        PaqueteResponseDto paquete_actualizado = paqueteService.updatePaquete(id,paqueteDto);
        return ResponseEntity.ok(paquete_actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paqueteService.deletePaquete(id);
        return ResponseEntity.noContent().build();
    }

}

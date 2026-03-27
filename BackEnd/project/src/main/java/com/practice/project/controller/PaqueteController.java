package com.practice.project.controller;

import com.practice.project.dtos.response.PaqueteDto;
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
    public ResponseEntity<PaqueteDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paqueteService.getPaquete(id));
    }

    @PostMapping
    public ResponseEntity<PaqueteDto> create(@Valid @RequestBody Paquete paquete) {
        PaqueteDto paquete1 = paqueteService.createPaquete(paquete);
        return ResponseEntity.status(201).body(paquete1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaqueteDto> update(@PathVariable Long id, @Valid @RequestBody Paquete paquete) {

        PaqueteDto paquete_actualizado = paqueteService.updatePaquete(id,paquete);
        return ResponseEntity.ok(paquete_actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paqueteService.deletePaquete(id);
        return ResponseEntity.noContent().build();
    }

}

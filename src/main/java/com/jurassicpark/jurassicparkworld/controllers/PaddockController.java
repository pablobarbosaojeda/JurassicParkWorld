package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/paddocks")
public class PaddockController {

    @Autowired
    PaddockRepository paddockRepository;

    // Obtener todos los paddocks
    @GetMapping
    public ResponseEntity<List<Paddock>> getAllPaddocks() {
        List<Paddock> paddocks = paddockRepository.findAll();
        return ResponseEntity.ok(paddocks);
    }

    // Obtener un paddock por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaddockById(@PathVariable Long id) {
        Optional<Paddock> paddock = paddockRepository.findById(id);
        if (paddock.isPresent()) {
            return ResponseEntity.ok(paddock.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo paddock
    @PostMapping
    public ResponseEntity<?> createPaddock(@RequestBody Paddock paddock) {
        if (paddock.getCapacity() <= 0) {
            return ResponseEntity.badRequest().body("Capacity must be greater than zero.");
        }
        Paddock savedPaddock = paddockRepository.save(paddock);
        return ResponseEntity.ok(savedPaddock);
    }

    // Actualizar un paddock existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaddock(@PathVariable Long id, @RequestBody Paddock updatedPaddock) {
        Optional<Paddock> existingPaddock = paddockRepository.findById(id);

        if (existingPaddock.isPresent()) {
            Paddock paddock = existingPaddock.get();
            paddock.setName(updatedPaddock.getName());
            paddock.setType(updatedPaddock.getType());
            paddock.setCapacity(updatedPaddock.getCapacity());
            paddock.setDinosaurs(updatedPaddock.getDinosaurs());
            paddockRepository.save(paddock);
            return ResponseEntity.ok(paddock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un paddock
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePaddock(@PathVariable Long id) {
        Optional<Paddock> paddock = paddockRepository.findById(id);

        if (paddock.isPresent()) {
            paddockRepository.delete(paddock.get());
            return ResponseEntity.ok("Paddock deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.services.InfirmaryService;
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

    @Autowired
    DinosaurRepository dinosaurRepository;

    @Autowired
    InfirmaryService infirmaryService; // Agregado para gestionar la enfermería

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

    // Obtener la matriz de un paddock
    @GetMapping("/{id}/matrix")
    public ResponseEntity<?> getPaddockMatrix(@PathVariable Long id) {
        Optional<Paddock> paddock = paddockRepository.findById(id);
        if (paddock.isPresent()) {
            paddock.get().updateMatrix(); // Asegurarse de que la matriz esté actualizada
            return ResponseEntity.ok(paddock.get().getMatrix());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mover un dinosaurio dentro de un paddock o transferirlo a la enfermería si está herido
    @PutMapping("/{paddockId}/dinosaurs/{dinosaurId}/move")
    public ResponseEntity<?> moveDinosaur(
            @PathVariable Long paddockId,
            @PathVariable Long dinosaurId,
            @RequestParam int newX,
            @RequestParam int newY) {

        Optional<Paddock> paddockOptional = paddockRepository.findById(paddockId);

        if (paddockOptional.isPresent()) {
            Paddock paddock = paddockOptional.get();
            Optional<Dinosaur> dinosaurOptional = paddock.getDinosaurs()
                    .stream()
                    .filter(d -> d.getId().equals(dinosaurId))
                    .findFirst();

            if (dinosaurOptional.isPresent()) {
                Dinosaur dinosaur = dinosaurOptional.get();

                // Si el dinosaurio está herido (salud < 50), lo trasladamos a la enfermería
                if (dinosaur.getHealth() < 50) {
                    infirmaryService.transferToInfirmary(dinosaur);
                    return ResponseEntity.ok("Dinosaur has been transferred to the infirmary.");
                } else {
                    // Si el dinosaurio no está herido, moverlo dentro del paddock
                    paddock.moveDinosaur(dinosaur, newX, newY);
                    paddockRepository.save(paddock); // Guardar cambios
                    return ResponseEntity.ok("Dinosaur moved successfully.");
                }
            } else {
                return ResponseEntity.badRequest().body("Dinosaur not found in this paddock.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
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
            paddock.setRows(updatedPaddock.getRows());
            paddock.setCols(updatedPaddock.getCols());
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

package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/dinosaurs")
public class DinosaurController {

    @Autowired
    DinosaurRepository dinosaurRepository;

    /**
     * Obtener todos los dinosaurios
     */
    @GetMapping
    public ResponseEntity<List<Dinosaur>> getAllDinosaurs() {
        List<Dinosaur> dinosaurs = dinosaurRepository.findAll();
        return ResponseEntity.ok(dinosaurs);
    }

    /**
     * Obtener un dinosaurio por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Dinosaur> getDinosaurById(@PathVariable Long id) {
        Optional<Dinosaur> dinosaur = dinosaurRepository.findById(id);
        return dinosaur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo dinosaurio
     */
    @PostMapping
    public ResponseEntity<Dinosaur> createDinosaur(@RequestBody Dinosaur dinosaur) {
        Dinosaur savedDinosaur = dinosaurRepository.save(dinosaur);
        return ResponseEntity.ok(savedDinosaur);
    }

    /**
     * Actualizar un dinosaurio existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Dinosaur> updateDinosaur(@PathVariable Long id, @RequestBody Dinosaur updatedDinosaur) {
        Optional<Dinosaur> existingDinosaur = dinosaurRepository.findById(id);

        if (existingDinosaur.isPresent()) {
            Dinosaur dinosaur = existingDinosaur.get();
            dinosaur.setName(updatedDinosaur.getName());
            dinosaur.setSpecies(updatedDinosaur.getSpecies());
            dinosaur.setType(updatedDinosaur.getType());
            dinosaur.setGender(updatedDinosaur.getGender());
            dinosaur.setAge(updatedDinosaur.getAge());
            dinosaur.setHealth(updatedDinosaur.getHealth());
            dinosaur.setHungerLevel(updatedDinosaur.getHungerLevel());
            Dinosaur savedDinosaur = dinosaurRepository.save(dinosaur);
            return ResponseEntity.ok(savedDinosaur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Alimentar a un dinosaurio
     */
    @PatchMapping("/{id}/feed")
    public ResponseEntity<Dinosaur> feedDinosaur(@PathVariable Long id) {
        Optional<Dinosaur> dinosaur = dinosaurRepository.findById(id);

        if (dinosaur.isPresent()) {
            Dinosaur dino = dinosaur.get();
            if (dino.isAlive()) {
                dino.feed();
                Dinosaur savedDino = dinosaurRepository.save(dino);
                return ResponseEntity.ok(savedDino);
            } else {
                return ResponseEntity.status(400).build(); // No se puede alimentar un dinosaurio muerto
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

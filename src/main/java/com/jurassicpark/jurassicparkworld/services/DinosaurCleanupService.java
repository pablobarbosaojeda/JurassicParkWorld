package com.jurassicpark.jurassicparkworld.services;

import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DinosaurCleanupService {

    @Autowired
    DinosaurRepository dinosaurRepository;

    // Eliminar dinosaurios muertos cada hora
    @Scheduled(fixedRate = 3600000) // Cada 1 hora (en milisegundos)
    public void removeDeadDinosaurs() {
        List<Dinosaur> deadDinosaurs = dinosaurRepository.findAll()
                .stream()
                .filter(dino -> !dino.isAlive()) // Filtrar los muertos
                .toList();

        if (!deadDinosaurs.isEmpty()) {
            dinosaurRepository.deleteAll(deadDinosaurs); // Eliminar todos los muertos
            System.out.printf("Removed %d dead dinosaurs from the system.%n", deadDinosaurs.size());
        }
    }
}


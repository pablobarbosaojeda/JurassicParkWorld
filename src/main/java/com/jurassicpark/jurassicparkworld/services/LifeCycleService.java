package com.jurassicpark.jurassicparkworld.services;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeCycleService {

    @Autowired
    DinosaurRepository dinosaurRepository;

    // Lógica del ciclo de vida
    @Scheduled(fixedRate = 60000) // Ejecuta cada 60 segundos
    public void simulateLifeCycle() {
        List<Dinosaur> dinosaurs = dinosaurRepository.findAll();

        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur.isAlive()) {
                dinosaur.ageUp();
                dinosaur.setHungerLevel(dinosaur.getHungerLevel() + 10);

                // Simula interacciones entre dinosaurios
                handleInteractions(dinosaur);

                // Comprueba si el dinosaurio muere de hambre
                if (dinosaur.getHungerLevel() >= 100) {
                    dinosaur.die();
                }
            }
        }

        dinosaurRepository.saveAll(dinosaurs);
    }

    private void handleInteractions(Dinosaur dinosaur) {
        List<Dinosaur> paddockDinosaurs = dinosaur.getPaddock().getDinosaurs();

        for (Dinosaur other : paddockDinosaurs) {
            if (!dinosaur.equals(other)) {
                if (dinosaur.isCarnivore() && !other.isCarnivore() && Math.random() > 0.5) {
                    other.injure(); // 50% de probabilidad de herir a un herbívoro
                } else if (dinosaur.isCarnivore() == other.isCarnivore() && Math.random() > 0.8) {
                    dinosaur.injure(); // Competencia entre carnívoros
                }
            }
        }
    }
}

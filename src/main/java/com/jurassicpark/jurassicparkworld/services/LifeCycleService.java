package com.jurassicpark.jurassicparkworld.services;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.services.InfirmaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

@Service
public class LifeCycleService implements Runnable {

    @Autowired
    private DinosaurRepository dinosaurRepository;

    @Autowired
    private InfirmaryService infirmaryService; // Servicio de enfermería

    private final Random random = new Random();

    private volatile boolean running = true; // Control del ciclo de vida

    @PostConstruct
    public void init() {
        Thread lifeCycleThread = new Thread(this);
        lifeCycleThread.setDaemon(true); // Hilo de fondo
        lifeCycleThread.start();
    }

    @Override
    public void run() {
        System.out.println("Starting life cycle simulation...");
        while (running) {
            try {
                simulateLifeCycle();
                Thread.sleep(10000); // Intervalo entre ciclos (7 segundos)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Life cycle simulation interrupted.");
            }
        }
    }

    public void stop() {
        running = false;
    }

    private void simulateLifeCycle() {
        List<Dinosaur> dinosaurs = dinosaurRepository.findAll();

        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur.isAlive()) {
                // Envejecer y aumentar el hambre
                dinosaur.ageUp();
                dinosaur.setHungerLevel(dinosaur.getHungerLevel() + 10);

                // Verificar hambre extrema y enviar a la enfermería si es necesario
                if (dinosaur.getHungerLevel() >= 100) {
                    dinosaur.die();
                    System.out.printf("%s has died of hunger.%n", dinosaur.getName());
                    continue; // Saltar interacciones para dinosaurios muertos
                }

                // Si la salud es baja (por debajo de un umbral), enviar a la enfermería
                if (dinosaur.getHealth() <= 30) {
                    infirmaryService.transferToInfirmary(dinosaur); // Llamada para transferir a la enfermería
                    continue; // No hacer más interacciones si está en la enfermería
                }

                // Manejar interacciones entre dinosaurios
                handleInteractions(dinosaur);
            }
        }

        // Limpiar dinosaurios muertos de la base de datos
        infirmaryService.removeDeadDinosaurs();

        // Guardar cambios en la base de datos
        dinosaurRepository.saveAll(dinosaurs);
        System.out.println("Cycle completed. Waiting for next interval...");
    }

    private void handleInteractions(Dinosaur dinosaur) {
        List<Dinosaur> paddockDinosaurs = dinosaur.getPaddock().getDinosaurs();

        for (Dinosaur other : paddockDinosaurs) {
            if (dinosaur.equals(other) || dinosaur.getName().equals(other.getName()) || !other.isAlive()) {
                continue; // Evitar interacción consigo mismo o con dinosaurios muertos
            }

            if (dinosaur.isCarnivore() && !other.isCarnivore()) {
                handleCarnivoreAttack(dinosaur, other);
            } else if (dinosaur.isCarnivore() && other.isCarnivore()) {
                handleCarnivoreCompetition(dinosaur, other);
            } else if (!dinosaur.isCarnivore() && random.nextDouble() > 0.9) {
                System.out.printf("%s interacts peacefully with %s.%n", dinosaur.getName(), other.getName());
            }
        }
    }

    private void handleCarnivoreAttack(Dinosaur carnivore, Dinosaur herbivore) {
        if (random.nextDouble() > 0.5) {
            carnivore.attack(herbivore);
            if (!herbivore.isAlive()) {
                System.out.printf("%s has killed %s.%n", carnivore.getName(), herbivore.getName());
            } else {
                System.out.printf("%s has injured %s.%n", carnivore.getName(), herbivore.getName());
            }
            // Si el herbívoro está herido, enviarlo a la enfermería
            if (herbivore.getHealth() <= 30) {
                infirmaryService.transferToInfirmary(herbivore);
            }
        }
    }

    private void handleCarnivoreCompetition(Dinosaur carnivore1, Dinosaur carnivore2) {
        if (random.nextDouble() > 0.5) {
            System.out.printf("Competition between %s and %s.%n", carnivore1.getName(), carnivore2.getName());
            if (random.nextDouble() > 0.5) {
                carnivore1.attack(carnivore2);
                if (!carnivore2.isAlive()) {
                    System.out.printf("%s has killed %s in competition.%n", carnivore1.getName(), carnivore2.getName());
                } else {
                    System.out.printf("%s injured %s in competition.%n", carnivore1.getName(), carnivore2.getName());
                }
            } else {
                carnivore2.attack(carnivore1);
                if (!carnivore1.isAlive()) {
                    System.out.printf("%s has killed %s in competition.%n", carnivore2.getName(), carnivore1.getName());
                } else {
                    System.out.printf("%s injured %s in competition.%n", carnivore2.getName(), carnivore1.getName());
                }
            }
        }
    }
}

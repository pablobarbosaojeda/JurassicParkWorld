package com.jurassicpark.jurassicparkworld.services;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class IncubatorService {

    private final Random random = new Random();

    @Autowired
    private PaddockRepository paddockRepository;

    @Autowired
    private DinosaurRepository dinosaurRepository;

    @Scheduled(fixedRate = 10000) // Cada 10 segundos
    public void generateDinosaur() {
        // Obtener especies únicas de dinosaurios existentes
        List<String> existingSpecies = dinosaurRepository.findAll()
                .stream()
                .map(Dinosaur::getSpecies)
                .distinct()
                .collect(Collectors.toList());

        if (existingSpecies.isEmpty()) {
            System.out.println("No species available to generate dinosaurs.");
            return;
        }

        // Seleccionar especie y tipo de forma aleatoria
        String species = existingSpecies.get(random.nextInt(existingSpecies.size()));
        String type = random.nextBoolean() ? "Carnivore" : "Herbivore"; // Aleatoriamente carnívoro o herbívoro
        String gender = random.nextBoolean() ? "Male" : "Female";
        String name = generateUniqueDinosaurName();

        Dinosaur newDinosaur = new Dinosaur();
        newDinosaur.setName(name);
        newDinosaur.setSpecies(species);
        newDinosaur.setType(type);
        newDinosaur.setGender(gender);
        newDinosaur.setAge(1); // Siempre comienzan con 1 año

        // Buscar un paddock adecuado para el dinosaurio
        List<Paddock> paddocks = paddockRepository.findAll();
        boolean assigned = false;

        for (Paddock paddock : paddocks) {
            if (isPaddockSuitable(paddock, type)) {
                paddock.getDinosaurs().add(newDinosaur);
                newDinosaur.setPaddock(paddock);
                dinosaurRepository.save(newDinosaur);
                paddockRepository.save(paddock);
                System.out.printf("New dinosaur %s (%s, %s) added to paddock %s.%n",
                        name, species, type, paddock.getName());
                assigned = true;
                break;
            }
        }

        if (!assigned) {
            System.out.println("No available paddocks for the new dinosaur. Incubator is waiting.");
        }
    }

    /**
     * Genera un nombre único para el dinosaurio.
     */
    private String generateUniqueDinosaurName() {
        String name;
        do {
            name = "Dino" + random.nextInt(10000);
        } while (dinosaurRepository.existsByName(name));
        return name;
    }

    /**
     * Verifica si un paddock es adecuado para un dinosaurio.
     */
    private boolean isPaddockSuitable(Paddock paddock, String dinosaurType) {
        return (paddock.getType().equalsIgnoreCase(dinosaurType) || paddock.getType().equalsIgnoreCase("Mixed")) &&
                paddock.getDinosaurs().size() < paddock.getCapacity();
    }
}

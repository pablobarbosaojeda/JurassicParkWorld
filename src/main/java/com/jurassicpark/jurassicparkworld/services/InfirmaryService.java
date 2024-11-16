package com.jurassicpark.jurassicparkworld.services;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Infirmary;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.Repositories.InfirmaryRepository;
import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InfirmaryService {

    @Autowired
    private InfirmaryRepository infirmaryRepository;

    @Autowired
    private DinosaurRepository dinosaurRepository;

    @Autowired
    private PaddockRepository paddockRepository;

    // Transferir dinosaurio herido a la enfermería
    @Transactional
    public void transferToInfirmary(Dinosaur dinosaur) {
        // Si el dinosaurio no está vivo o no está lo suficientemente herido, no se transfiere
        if (!dinosaur.isAlive() || dinosaur.getHealth() > 50) {
            System.out.printf("%s does not need to be transferred to the infirmary.%n", dinosaur.getName());
            return;
        }

        // Obtener todas las enfermerías
        List<Infirmary> infirmaries = infirmaryRepository.findAll();
        for (Infirmary infirmary : infirmaries) {
            if (infirmary.hasSpace()) {
                // Si hay espacio en la enfermería, transferir el dinosaurio
                infirmary.addDinosaur(dinosaur);
                dinosaur.setInfirmary(infirmary);
                dinosaurRepository.save(dinosaur);
                infirmaryRepository.save(infirmary);
                System.out.printf("%s has been transferred to %s.%n", dinosaur.getName(), infirmary.getName());
                return;
            }
        }
        // Si no hay espacio, mostrar mensaje
        System.out.printf("No space available in any infirmary for %s.%n", dinosaur.getName());
    }

    // Curar a todos los dinosaurios en la enfermería
    @Transactional
    public void healAll() {
        List<Infirmary> infirmaries = infirmaryRepository.findAll();
        for (Infirmary infirmary : infirmaries) {
            List<Dinosaur> dinosaurs = infirmary.getDinosaurs();
            for (Dinosaur dinosaur : dinosaurs) {
                if (dinosaur.isInNursing()) {
                    dinosaur.setHealth(100); // Restaurar salud
                    dinosaur.setInNursing(false); // Marcar como curado
                    dinosaurRepository.save(dinosaur);
                    System.out.printf("%s has been healed.%n", dinosaur.getName());
                }
            }
        }
    }

    // Devolver dinosaurios curados a sus paddocks
    @Transactional
    public void returnDinosaursToPaddocks() {
        List<Infirmary> infirmaries = infirmaryRepository.findAll();
        for (Infirmary infirmary : infirmaries) {
            List<Dinosaur> dinosaurs = infirmary.getDinosaurs();
            for (Dinosaur dinosaur : dinosaurs) {
                List<Paddock> paddocks = paddockRepository.findAll();
                for (Paddock paddock : paddocks) {
                    if (paddock.getDinosaurs().size() < paddock.getCapacity() &&
                            (paddock.getType().equalsIgnoreCase(dinosaur.getType()) ||
                                    paddock.getType().equalsIgnoreCase("Mixed"))) {
                        dinosaur.setPaddock(paddock);
                        paddock.getDinosaurs().add(dinosaur);
                        dinosaur.setInfirmary(null);
                        paddockRepository.save(paddock);
                        dinosaurRepository.save(dinosaur);
                        System.out.printf("%s has been returned to paddock %s.%n", dinosaur.getName(), paddock.getName());
                        break;
                    }
                }
            }
        }
    }

    // NUEVO: Método para manejar dinosaurios muertos en la enfermería
    @Transactional
    public void removeDeadDinosaurs() {
        List<Infirmary> infirmaries = infirmaryRepository.findAll();
        for (Infirmary infirmary : infirmaries) {
            List<Dinosaur> dinosaurs = infirmary.getDinosaurs();
            dinosaurs.removeIf(dinosaur -> !dinosaur.isAlive()); // Eliminar dinosaurios muertos
            infirmaryRepository.save(infirmary);
        }
    }

    // NUEVO: Obtener todas las enfermerías
    public List<Infirmary> getAllInfirmaries() {
        return infirmaryRepository.findAll();
    }

    // NUEVO: Curar dinosaurio específico por ID
    @Transactional
    public void healDinosaur(Long dinosaurId) {
        Optional<Dinosaur> optionalDinosaur = dinosaurRepository.findById(dinosaurId);
        if (optionalDinosaur.isEmpty()) {
            throw new IllegalArgumentException("Dinosaur with ID " + dinosaurId + " does not exist.");
        }

        Dinosaur dinosaur = optionalDinosaur.get();

        if (!dinosaur.isInNursing()) {
            throw new IllegalStateException("Dinosaur with ID " + dinosaurId + " is not in an infirmary.");
        }

        // Restaurar salud y marcar como curado
        dinosaur.setHealth(100);
        dinosaur.setInNursing(false);

        // Retirar dinosaurio de la enfermería
        Infirmary infirmary = infirmaryRepository.findByDinosaursContains(dinosaur)
                .orElseThrow(() -> new IllegalStateException("Dinosaur is not assigned to any infirmary."));
        infirmary.getDinosaurs().remove(dinosaur);
        infirmaryRepository.save(infirmary);

        dinosaurRepository.save(dinosaur);

        System.out.printf("%s has been healed and removed from infirmary %s.%n", dinosaur.getName(), infirmary.getName());
    }
}

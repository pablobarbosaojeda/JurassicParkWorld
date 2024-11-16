package com.jurassicpark.jurassicparkworld.component;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.models.Park;
import com.jurassicpark.jurassicparkworld.models.Visitor;
import com.jurassicpark.jurassicparkworld.Repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import com.jurassicpark.jurassicparkworld.Repositories.ParkRepository;
import com.jurassicpark.jurassicparkworld.Repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    DinosaurRepository dinosaurRepository;

    @Autowired
    VisitorRepository visitorRepository;

    @Autowired
    PaddockRepository paddockRepository;

    @Autowired
    ParkRepository parkRepository;

    public DataLoader() {}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Crear el parque
        Park park = new Park(200);
        parkRepository.save(park);

        // Crear visitantes y asociarlos al parque
        Visitor visitor1 = new Visitor("12/02/2019", park);
        visitorRepository.save(visitor1);
        park.addVisitors(visitor1);

        // Crear paddocks con filas y columnas personalizadas
        Paddock paddock1 = new Paddock("Jungle", "Carnivore", 4, "Abierto", 10, 10);
        Paddock paddock2 = new Paddock("Rainforest", "Herbivore", 4, "Abierto", 12, 12);
        Paddock paddockNublar = new Paddock("Nublar", "Mixed", 6, "Abierto", 15, 15);

        // Crear una enfermería con capacidad ilimitada (especial)
        Paddock infirmary = new Paddock("Infirmary", "Mixed", 100, "Abierto", 10, 10);

        paddockRepository.save(paddock1);
        paddockRepository.save(paddock2);
        paddockRepository.save(paddockNublar);
        paddockRepository.save(infirmary);

        // Crear dinosaurios y asociarlos a los paddocks
        Dinosaur dinosaur1 = new Dinosaur("Georgina", "Cerasinops", 5, "Female", "Herbivore", paddock2);
        Dinosaur dinosaur2 = new Dinosaur("David", "Microceratops", 10, "Male", "Carnivore", paddock1);

        // Asignar posiciones iniciales en la matriz
        dinosaur1.setPosX(3);
        dinosaur1.setPosY(4);
        dinosaur2.setPosX(1);
        dinosaur2.setPosY(2);

        dinosaurRepository.save(dinosaur1);
        dinosaurRepository.save(dinosaur2);

        // Actualizar las matrices de los paddocks
        paddock1.updateMatrix();
        paddock2.updateMatrix();
        paddockNublar.updateMatrix();

        // Restaurar dinosaurios heridos al iniciar
        restoreInfirmaryDinos(infirmary, List.of(paddock1, paddock2, paddockNublar));
    }

    /**
     * Restaurar dinosaurios heridos desde la enfermería a los paddocks disponibles.
     */
    private void restoreInfirmaryDinos(Paddock infirmary, List<Paddock> paddocks) {
        List<Dinosaur> injuredDinos = infirmary.getDinosaurs();

        for (Dinosaur dino : injuredDinos) {
            dino.setHealth(100); // Restaurar salud
            dino.setPaddock(null); // Liberar de la enfermería temporalmente

            // Reubicar al dinosaurio en un paddock disponible si no está en la incubadora
            if (!dino.isInNursing()) {
                for (Paddock paddock : paddocks) {
                    if (paddock.getDinosaurs().size() < paddock.getCapacity()) {
                        dino.setPaddock(paddock);
                        paddock.getDinosaurs().add(dino);
                        paddock.updateMatrix();
                        break;
                    }
                }
            }

            dinosaurRepository.save(dino);
        }

        // Limpiar la enfermería
        infirmary.getDinosaurs().clear();
        paddockRepository.save(infirmary);
    }
}

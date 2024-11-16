package com.jurassicpark.jurassicparkworld.services;


import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class MovementService {

    private final Random random = new Random();

    @Autowired
    private PaddockRepository paddockRepository;

    public void simulateMovementForAllPaddocks() {
        List<Paddock> paddocks = paddockRepository.findAll(); // Obtener todos los paddocks
        for (Paddock paddock : paddocks) {
            simulateMovement(paddock);
            paddockRepository.save(paddock); // Guardar los cambios en la base de datos
        }
    }

    private void simulateMovement(Paddock paddock) {
        if (paddock == null || paddock.getMatrix() == null || paddock.getDinosaurs() == null) {
            return; // Validación para evitar errores
        }

        for (Dinosaur dinosaur : paddock.getDinosaurs()) {
            if (dinosaur.isAlive()) { // Mover solo dinosaurios vivos
                int currentX = dinosaur.getPosX();
                int currentY = dinosaur.getPosY();

                // Generar nuevas posiciones aleatorias dentro de los límites
                int newX = Math.max(0, Math.min(currentX + random.nextInt(3) - 1, paddock.getRows() - 1));
                int newY = Math.max(0, Math.min(currentY + random.nextInt(3) - 1, paddock.getCols() - 1));

                // Si la nueva posición es diferente, actualiza
                if (newX != currentX || newY != currentY) {
                    paddock.getMatrix()[currentX][currentY] = null; // Limpia la posición anterior
                    dinosaur.setPosX(newX);
                    dinosaur.setPosY(newY);
                    paddock.getMatrix()[newX][newY] = "D"; // Marca la nueva posición
                }
            }
        }
    }
}

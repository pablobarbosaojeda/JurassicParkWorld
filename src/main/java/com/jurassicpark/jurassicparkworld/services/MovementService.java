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
            if (paddock != null && paddock.getDinosaurs() != null) {
                simulateMovement(paddock);
                paddockRepository.save(paddock); // Guardar los cambios en la base de datos
            }
        }
    }

    private void simulateMovement(Paddock paddock) {
        int rows = 10; // Tamaño fijo de la matriz en el frontend
        int cols = 10;

        for (Dinosaur dinosaur : paddock.getDinosaurs()) {
            if (dinosaur.isAlive()) { // Mover solo dinosaurios vivos
                int currentX = dinosaur.getPosX();
                int currentY = dinosaur.getPosY();

                // Generar nuevas posiciones aleatorias dentro de los límites
                int newX = generateNewPosition(currentX, rows);
                int newY = generateNewPosition(currentY, cols);

                // Validar y actualizar posición solo si es diferente
                if (newX != currentX || newY != currentY) {
                    System.out.printf("Dinosaur %s moved from (%d, %d) to (%d, %d)%n",
                            dinosaur.getName(), currentX, currentY, newX, newY);
                    dinosaur.setPosX(newX);
                    dinosaur.setPosY(newY);
                }
            }
        }
    }

    private int generateNewPosition(int current, int maxLimit) {
        // Genera un nuevo valor aleatorio dentro del rango permitido
        return Math.max(0, Math.min(current + random.nextInt(3) - 1, maxLimit - 1));
    }
}

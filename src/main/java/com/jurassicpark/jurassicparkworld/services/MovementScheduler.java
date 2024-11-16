package com.jurassicpark.jurassicparkworld.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MovementScheduler {

    @Autowired
    private MovementService movementService;

    // Ejecutar cada 5 segundos
    @Scheduled(fixedRate = 5000)
    public void moveDinosaursPeriodically() {
        movementService.simulateMovementForAllPaddocks();
    }
}

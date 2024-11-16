package com.jurassicpark.jurassicparkworld.Repositories;

import com.jurassicpark.jurassicparkworld.models.Infirmary;
import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InfirmaryRepository extends JpaRepository<Infirmary, Long> {
    Optional<Infirmary> findByDinosaursContains(Dinosaur dinosaur);
}

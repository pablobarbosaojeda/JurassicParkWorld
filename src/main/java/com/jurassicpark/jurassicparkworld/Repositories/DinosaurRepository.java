package com.jurassicpark.jurassicparkworld.Repositories;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.Projections.EmbedDinosaurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = EmbedDinosaurs.class)
public interface  DinosaurRepository extends JpaRepository<Dinosaur, Long> {

 /**
  * Comprueba si existe un dinosaurio con el nombre especificado.
  */
 boolean existsByName(String name);

 /**
  * Encuentra todos los dinosaurios muertos (alive = false).
  */
 List<Dinosaur> findAllByAliveFalse();  // Nuevo método para obtener dinosaurios muertos
}

package com.jurassicpark.jurassicparkworld.Repositories;

import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.Projections.EmbedPaddocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(excerptProjection = EmbedPaddocks.class)
public interface PaddockRepository extends JpaRepository<Paddock,Long> {

}

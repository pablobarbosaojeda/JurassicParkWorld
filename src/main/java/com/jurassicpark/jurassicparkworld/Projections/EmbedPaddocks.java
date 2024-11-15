package com.jurassicpark.jurassicparkworld.Projections;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name= "embedPaddocks", types = Paddock.class)
public interface EmbedPaddocks {
        long getId();
        String getName();
        String getType();
        int getCapacity();
        List<Dinosaur> getDinosaurs();
}

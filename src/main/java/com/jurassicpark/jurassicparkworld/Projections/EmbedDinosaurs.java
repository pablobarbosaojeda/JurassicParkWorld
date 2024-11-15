package com.jurassicpark.jurassicparkworld.Projections;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import org.springframework.data.rest.core.config.Projection;

@Projection(name= "embedDinosaurs", types = Dinosaur.class)
public interface EmbedDinosaurs {
    long getId();
    String getName();
    String getSpecies();
    int getBelly();
    void setBelly(int belly);
    String getGender();
    int getAge();
    String getType();
    Paddock getPaddock();
    String getImg();
    void setPaddock(Paddock paddock);

}

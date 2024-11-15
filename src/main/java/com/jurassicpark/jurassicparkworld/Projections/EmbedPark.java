package com.jurassicpark.jurassicparkworld.Projections;

import com.jurassicpark.jurassicparkworld.models.Park;
import org.springframework.data.rest.core.config.Projection;

@Projection(name= "embedPark", types = Park.class)
public interface EmbedPark {
    long getId();
   double getDailyRevenue();
    int getVisitorCount();

}

package com.jurassicpark.jurassicparkworld.Projections;

import com.jurassicpark.jurassicparkworld.models.Visitor;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name= "embedVisitors", types = Visitor.class)
public interface EmbedVisitors {

}

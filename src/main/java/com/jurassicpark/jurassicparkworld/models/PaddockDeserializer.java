package com.jurassicpark.jurassicparkworld.models;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PaddockDeserializer extends JsonDeserializer<Paddock> {

    @Autowired
    private PaddockRepository paddockRepository; // Ajusta el repositorio a tu implementación

    @Override
    public Paddock deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String url = jsonParser.getText();

        // Extraer el ID desde la URL
        Long id = extractIdFromUrl(url);

        // Buscar el Paddock por ID en la base de datos
        return paddockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paddock not found for ID: " + id));
    }

    private Long extractIdFromUrl(String url) {
        // Extrae el ID del final de la URL (por ejemplo, "http://localhost:8080/api/paddocks/1")
        String[] parts = url.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }
}

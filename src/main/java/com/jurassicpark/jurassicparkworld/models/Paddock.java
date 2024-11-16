package com.jurassicpark.jurassicparkworld.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "paddocks")
@JsonDeserialize(using = PaddockDeserializer.class) // Configuración para el deserializador personalizado
public class Paddock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnoreProperties("paddock")
    @OneToMany(mappedBy = "paddock", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dinosaur> dinosaurs;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    // Constructor principal
    public Paddock(String name, String type, int capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.dinosaurs = new ArrayList<>();
    }

    // Constructor vacío
    public Paddock() {
        this.dinosaurs = new ArrayList<>();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
        // Sincroniza el lado inverso de la relación
        for (Dinosaur dinosaur : dinosaurs) {
            dinosaur.setPaddock(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addDinosaur(Dinosaur dinosaur) {
        if (this.dinosaurs.size() < this.capacity) {
            this.dinosaurs.add(dinosaur);
            dinosaur.setPaddock(this); // Sincronización bidireccional
        } else {
            throw new IllegalStateException("Paddock is at full capacity!");
        }
    }

    public void removeDinosaur(Dinosaur dinosaur) {
        this.dinosaurs.remove(dinosaur);
        dinosaur.setPaddock(null); // Sincronización bidireccional
    }
}

package com.jurassicpark.jurassicparkworld.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "infirmaries")
public class Infirmary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "infirmary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dinosaur> dinosaurs = new ArrayList<>();

    // Constructor principal
    public Infirmary(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    // Constructor vacío
    public Infirmary() {
    }

    // Verificar si la enfermería tiene espacio disponible
    public boolean hasSpace() {
        return capacity == Integer.MAX_VALUE || dinosaurs.size() < capacity;
    }

    // Añadir dinosaurio a la enfermería
    public void addDinosaur(Dinosaur dinosaur) {
        if (hasSpace()) {
            dinosaurs.add(dinosaur);
            dinosaur.setInfirmary(this);
        } else {
            throw new IllegalStateException("No space available in the infirmary.");
        }
    }

    // Eliminar dinosaurio de la enfermería
    public void removeDinosaur(Dinosaur dinosaur) {
        dinosaurs.remove(dinosaur);
        dinosaur.setInfirmary(null);
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }
}

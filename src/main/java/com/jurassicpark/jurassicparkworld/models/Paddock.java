package com.jurassicpark.jurassicparkworld.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="paddocks")
public class Paddock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @JsonIgnoreProperties("paddocks")
    @OneToMany(mappedBy = "paddock", fetch = FetchType.LAZY)
    private List<Dinosaur> dinosaurs;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name="capacity")
    private int capacity;

    public Paddock(String name, String type, int capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.dinosaurs=new ArrayList<Dinosaur>();
    }

    public Paddock() {
    }

    public List<Dinosaur> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(ArrayList<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
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
    public void addDinosaur(Dinosaur dinosaur){
        if (this.capacity > dinosaurs.size()){
            this.dinosaurs.add(dinosaur);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}

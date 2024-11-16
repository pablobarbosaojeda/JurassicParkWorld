package com.jurassicpark.jurassicparkworld.models;

import java.util.UUID;

public class Sensor {

    private UUID id;
    private String name;
    private String description;
    private String status;

    public Sensor(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.status = "Active";
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

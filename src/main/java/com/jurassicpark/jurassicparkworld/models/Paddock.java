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

    @Column(name = "enclosureStatus", nullable = false)
    private String enclosureStatus; // Abierto, Cerrado, En Reparación

    @Column(name = "hazardLevel")
    private int hazardLevel; // Nivel de peligro del paddock (0-10)

    @Transient
    private String[][] matrix; // Matriz para representar el paddock

    private int rows; // Número de filas en la matriz
    private int cols; // Número de columnas en la matriz

    // Constructor principal
    public Paddock(String name, String type, int capacity, String enclosureStatus, int rows, int cols) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.enclosureStatus = enclosureStatus;
        this.hazardLevel = 0; // Valor inicial
        this.dinosaurs = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
        this.matrix = new String[rows][cols];
        initializeMatrix();
    }

    // Constructor vacío
    public Paddock() {
        this.dinosaurs = new ArrayList<>();
        this.hazardLevel = 0; // Valor inicial
        this.rows = 10; // Tamaño predeterminado
        this.cols = 10; // Tamaño predeterminado
        this.matrix = new String[rows][cols];
        initializeMatrix();
    }

    // Inicializar la matriz con valores predeterminados
    private void initializeMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = "-"; // Vacío por defecto
            }
        }
    }

    // Método para mover un dinosaurio dentro de la matriz
    public void moveDinosaur(Dinosaur dinosaur, int newX, int newY) {
        // Validar límites de la matriz
        if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
            matrix[dinosaur.getPosX()][dinosaur.getPosY()] = "-"; // Limpiar posición anterior
            dinosaur.moveTo(newX, newY); // Actualizar posición del dinosaurio
            matrix[newX][newY] = "D"; // Marcar nueva posición
        }
    }

    // Método para actualizar la matriz con los dinosaurios actuales
    public void updateMatrix() {
        initializeMatrix(); // Reiniciar la matriz
        for (Dinosaur dinosaur : dinosaurs) {
            if (dinosaur.isAlive()) {
                matrix[dinosaur.getPosX()][dinosaur.getPosY()] = "D"; // Marcar dinosaurio vivo
            }
        }
    }

    // Métodos existentes para gestionar dinosaurios
    public void addDinosaur(Dinosaur dinosaur) {
        if ("Mixed".equals(this.type) || this.type.equalsIgnoreCase(dinosaur.getType())) {
            if (this.dinosaurs.size() < this.capacity) {
                this.dinosaurs.add(dinosaur);
                dinosaur.setPaddock(this); // Sincronización bidireccional
                calculateHazardLevel(); // Recalcular el nivel de peligro
                updateMatrix(); // Actualizar la matriz
            } else {
                throw new IllegalStateException("Paddock is at full capacity!");
            }
        }
    }

    public void removeDinosaur(Dinosaur dinosaur) {
        this.dinosaurs.remove(dinosaur);
        dinosaur.setPaddock(null); // Sincronización bidireccional
        calculateHazardLevel(); // Recalcular el nivel de peligro
        updateMatrix(); // Actualizar la matriz
    }

    // Métodos existentes para gestión avanzada
    public void calculateHazardLevel() {
        this.hazardLevel = 0;
        for (Dinosaur dino : dinosaurs) {
            if (dino.isCarnivore()) {
                this.hazardLevel += 2; // Aumenta el nivel de peligro por carnívoros
            }
            if (dino.getHealth().equals("Herido")) {
                this.hazardLevel += 1; // Heridos aumentan ligeramente el peligro
            }
        }
        if (this.hazardLevel > 10) {
            this.hazardLevel = 10; // Máximo nivel de peligro
        }
    }

    public boolean isOverCapacity() {
        return this.dinosaurs.size() > this.capacity;
    }

    public boolean isSafe() {
        return this.hazardLevel < 5; // Menor de 5 se considera seguro
    }

    // Getters y Setters para nuevos atributos
    public String[][] getMatrix() {
        return matrix;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
        this.matrix = new String[rows][cols];
        initializeMatrix();
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
        this.matrix = new String[rows][cols];
        initializeMatrix();
    }

    // Getters y Setters existentes
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
        for (Dinosaur dinosaur : dinosaurs) {
            dinosaur.setPaddock(this);
        }
        calculateHazardLevel(); // Recalcular el nivel de peligro
        updateMatrix(); // Actualizar la matriz
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

    public String getEnclosureStatus() {
        return enclosureStatus;
    }

    public void setEnclosureStatus(String enclosureStatus) {
        this.enclosureStatus = enclosureStatus;
    }

    public int getHazardLevel() {
        return hazardLevel;
    }

    public void setHazardLevel(int hazardLevel) {
        this.hazardLevel = hazardLevel;
    }
}

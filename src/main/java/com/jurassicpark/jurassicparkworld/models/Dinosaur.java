package com.jurassicpark.jurassicparkworld.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.HashMap;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "dinosaurs")
public class Dinosaur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "belly")
    private int belly;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "type")
    private String type; // "Carnivore", "Herbivore", etc.

    @Column(name = "img")
    private String img;

    @Column(name = "health", nullable = false)
    private String health = "Sano"; // Valor predeterminado

    @Column(name = "hunger_level", nullable = false)
    private int hungerLevel = 0; // Valor predeterminado

    @Column(name = "pos_x", nullable = false)
    private int posX; // Posición X del dinosaurio dentro del paddock

    @Column(name = "pos_y", nullable = false)
    private int posY; // Posición Y del dinosaurio dentro del paddock

    @JsonIgnoreProperties("dinosaurs")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pad_id")
    private Paddock paddock;

    // Constructor principal
    public Dinosaur(String name, String species, int belly, String gender, String type, Paddock paddock) {
        this.name = name;
        this.species = species;
        this.belly = belly;
        this.gender = gender;
        this.age = 0;
        this.type = type;
        this.paddock = paddock;
        this.img = getImgUrl(species);
        this.health = "Sano"; // Valor predeterminado
        this.hungerLevel = 0; // Valor predeterminado
        this.posX = 0; // Posición inicial por defecto
        this.posY = 0; // Posición inicial por defecto
    }

    // Constructor vacío
    public Dinosaur() {
        this.health = "Sano"; // Asegura que health nunca sea null
        this.hungerLevel = 0; // Inicialización predeterminada
        this.posX = 0; // Posición inicial por defecto
        this.posY = 0; // Posición inicial por defecto
    }

    // Métodos nuevos para manejar posición
    public void moveTo(int newX, int newY) {
        this.posX = newX;
        this.posY = newY;
    }

    // Métodos nuevos para obtener la posición
    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    // Métodos existentes
    public void ageUp() {
        this.age++;
    }

    public void feed() {
        this.hungerLevel = 0;
    }

    public void injure() {
        if (this.health != null && this.health.equals("Sano")) {
            this.health = "Herido";
        }
    }

    public void die() {
        this.health = "Muerto";
    }

    public boolean isAlive() {
        return this.health != null && !this.health.equals("Muerto");
    }

    public boolean isCarnivore() {
        return this.type != null && this.type.equalsIgnoreCase("Carnivore");
    }

    // Getters y Setters existentes
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
        this.img = getImgUrl(species);
    }

    public int getBelly() {
        return belly;
    }

    public void setBelly(int belly) {
        this.belly = belly;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public Paddock getPaddock() {
        return paddock;
    }

    public void setPaddock(Paddock paddock) {
        // Sincronización bidireccional para evitar pérdida de datos
        if (this.paddock != null) {
            this.paddock.getDinosaurs().remove(this);
        }
        this.paddock = paddock;
        if (paddock != null) {
            paddock.getDinosaurs().add(this);
        }
    }

    // Método para obtener URL de la imagen
    public String getImgUrl(String species) {
        HashMap<String, String> imgMap = new HashMap<>();
        imgMap.put("Cerasinops", "https://upload.wikimedia.org/wikipedia/commons/d/d5/Cerasinops_BW.jpg");
        imgMap.put("Archaeoceratops", "https://alchetron.com/cdn/auroraceratops-cc5392db-8159-466e-94c3-775d5660da3-resize-750.jpg");
        imgMap.put("Microceratops", "https://i.pinimg.com/originals/51/e8/32/51e832eb88f5032311944fc8a40ffd80.png");
        imgMap.put("Leptoceratops", "http://www.dinosaurusi.com/video_slike/Vj9xlC7mwS-Leptoceratops,_dinosaurs,_Cretaceous_Period,_herbivores-011.jpg");
        imgMap.put("Bagaceratops", "https://vignette.wikia.nocookie.net/devon-dink-dino/images/f/f3/Protoceratops.jpg/revision/latest?cb=20130404232541");
        imgMap.put("Protoceratops", "https://cdn.thinglink.me/api/image/583409781968994305/1240/10/scaletowidth");
        imgMap.put("Dracorex", "https://www.schleich-s.com/media/360/content/15014/spin/650/001.jpg");

        return imgMap.getOrDefault(species, "https://via.placeholder.com/150");
    }
}

package com.jurassicpark.jurassicparkworld.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

@Entity
@Table(name ="dinosaurs")
public class Dinosaur
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column (name ="name")
    private String name;

    @Column (name ="species")
    private String species;

    @Column (name ="belly")
    private int belly;

    @Column (name ="gender")
    private String gender;

    @Column (name ="age")
    private int age;

    @Column(name = "type")
    private String type;
    @Column(name = "img")
    private String img;

    @JsonIgnoreProperties("dinosaurs")
    @ManyToOne
    @JoinColumn(name = "pad_id")
    private Paddock paddock;


    public Dinosaur(String name, String species, int belly, String gender, String type, Paddock paddock) {
        this.name = name;
        this.species = species;
        this.belly = belly;
        this.gender = gender;
        this.age = 0;
        this.type = type;
        this.paddock = paddock;
        this.img = getImgUrl(species);
    }

    public Dinosaur() {
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

    public Paddock getPaddock() {
        return paddock;
    }

    public void setPaddock(Paddock paddock) {
        this.paddock = paddock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String  getImgUrl(String species ){
         String url = null;
        HashMap<String , String> hm = new HashMap<String, String>();
        hm.put("Cerasinops", "https://upload.wikimedia.org/wikipedia/commons/d/d5/Cerasinops_BW.jpg");
        hm.put("Archaeoceratops", "https://alchetron.com/cdn/auroraceratops-cc5392db-8159-466e-94c3-775d5660da3-resize-750.jpg");
        hm.put("Microceratops", "https://i.pinimg.com/originals/51/e8/32/51e832eb88f5032311944fc8a40ffd80.png");
        hm.put("Leptoceratops","http://www.dinosaurusi.com/video_slike/Vj9xlC7mwS-Leptoceratops,_dinosaurs,_Cretaceous_Period,_herbivores-011.jpg");
        hm.put("Bagaceratops","https://vignette.wikia.nocookie.net/devon-dink-dino/images/f/f3/Protoceratops.jpg/revision/latest?cb=20130404232541");
        hm.put("Protoceratops","https://cdn.thinglink.me/api/image/583409781968994305/1240/10/scaletowidth");
        hm.put("Leaellynasaura","");
        hm.put("Dracorex","https://www.schleich-s.com/media/360/content/15014/spin/650/001.jpg");
        if (hm.containsKey(species)){
           url = hm.get(species);
      }
      return this.img = url;
    }

    public String getImg() {
        return img;
    }

//    public void setImg(String img) {
//        this.img = img;
//    }
}

package com.jurassicpark.jurassicparkworld.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name ="visitors")
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private String date;


    @JsonIgnoreProperties("visitors")
    @ManyToOne
    @JoinColumn(name = "park_id", nullable=false)
    private Park park ;

    public Visitor() {
    }

    public Visitor(String date,Park park ) {
        this.date = date;
        this.park= park;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Long getId() {
        return id;
    }
    public Park getPark() {
        return park;
    }

    public void setPark(Park park) {
        this.park = park;
    }


}

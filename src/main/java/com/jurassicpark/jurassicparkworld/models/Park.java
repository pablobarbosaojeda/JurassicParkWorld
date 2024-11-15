package com.jurassicpark.jurassicparkworld.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="parks")
public class Park {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @Column(name = "dailyRevenue")
//    private double dailyRevenue;

    @Column(name = "totalRevenue")
    private double totalRevenue;
//
//    @Column(name = "date")
//    private String  date;
//
    @Column(name = "visitorCount")
    private int  visitorCount;

    @JsonIgnoreProperties("park")
    @OneToMany(mappedBy = "park", fetch = FetchType.LAZY)
    private List<Visitor> visitors;


    public Park(double totalRevenue) {


//        this.dailyRevenue =0;
        this.totalRevenue=totalRevenue;

        this.visitors= new ArrayList<>();
        this.visitorCount = 0;
    }

    public Park() {
    }
//        public double getDailyRevenue() {
//
//        return this.dailyRevenue =this.visitors.size()* 5;
//    }

//    public void setDailyRevenue(double dailyRevenue) {
//        this.dailyRevenue = dailyRevenue;
//    }
//
//    public double getVisitorCount() {
//        return this.visitorCount=this.visitors.size();
//    }

    public double getTotalRevenue() {
//        totalRevenue+=dailyRevenue;
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    public void  addVisitors(Visitor visitor){
      this.visitors.add(visitor);
    }


//    public int  getVisitorsByName(String date ){
//        int  count =0;
//     for (Visitor visitor : visitors) {
//         if (visitor.getDate().equals(date)){
//             count +=1;
//         }
//     }
//     return this.visitorCount=count ;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }
}

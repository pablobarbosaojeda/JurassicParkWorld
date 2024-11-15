package com.jurassicpark.jurassicparkworld.models;


import java.util.List;

public class FinancialRecord {
    private String date ;
    private double dailyRevenue ;
    private List<Visitor> visitors ;
    private Park park;

    public FinancialRecord(String date, double dailyRevenue, List<Visitor>  visitors ) {
        this.date = date;
        this.dailyRevenue = dailyRevenue;
        this.visitors = visitors;
    }

    public String getDate() {
        return date;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public double getDailyRevenue() {
        return dailyRevenue;
    }

    public void setDailyRevenue(double dailyRevenue) {
        this.dailyRevenue = dailyRevenue;
    }

    public List<Visitor>  getVisitors() {
        return visitors;
    }

    public void setVisitors(List<Visitor>  visitors) {
        this.visitors = visitors;
    }

}

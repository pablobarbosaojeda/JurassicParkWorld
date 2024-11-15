package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.models.Park;
import com.jurassicpark.jurassicparkworld.repositories.ParkRepository;
import com.jurassicpark.jurassicparkworld.repositories.VisitorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/park")
public class ParkController {
//    @Autowired
//    VisitorRepository visitorRepository;
//
//    @GetMapping
//    public Park getAllPaddockAndVistors(){
//    List<Visitor> visitors = visitorRepository.findAll();
//    int  visitorCount = visitors.size();
//    double totalRevenue = visitorCount * 5;
//
//
//    Park park = new Park(,visitorCount,totalRevenue);
//        return park;
//    }

    @Autowired
    ParkRepository parkRepository;

}


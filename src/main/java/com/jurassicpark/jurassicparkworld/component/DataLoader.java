package com.jurassicpark.jurassicparkworld.component;

import com.jurassicpark.jurassicparkworld.models.Dinosaur;
import com.jurassicpark.jurassicparkworld.models.Paddock;
import com.jurassicpark.jurassicparkworld.models.Park;
import com.jurassicpark.jurassicparkworld.models.Visitor;
import com.jurassicpark.jurassicparkworld.repositories.DinosaurRepository;
import com.jurassicpark.jurassicparkworld.repositories.PaddockRepository;
import com.jurassicpark.jurassicparkworld.repositories.ParkRepository;
import com.jurassicpark.jurassicparkworld.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner{

        @Autowired
        DinosaurRepository dinosaurRepository;
        @Autowired
        VisitorRepository visitorRepository;
        @Autowired
        PaddockRepository paddockRepository;
        @Autowired
         ParkRepository parkRepository;
    public DataLoader() {
    }

    public void run(ApplicationArguments args) throws Exception  {
        Park park = new Park (200);
        parkRepository.save(park);



        Visitor visitor1 = new Visitor("12/02/2019",park);
        visitorRepository.save(visitor1);

        park.addVisitors(visitor1);

        Paddock paddock1 = new Paddock("Jungle","Carnivore", 4);
        Paddock paddock2 = new Paddock("Rainforest","Herbivore", 4);
        paddockRepository.save(paddock1);
        paddockRepository.save(paddock2);


        Dinosaur dinosaur1= new Dinosaur("Georgina","Cerasinops",5,"Female","Herbivore",paddock1);
        Dinosaur dinosaur2= new Dinosaur("David", "Microceratops",10,"Male","Carnivore",paddock1);


        dinosaurRepository.save(dinosaur1);
        dinosaurRepository.save(dinosaur2);

    }

    /*public static String getDate()
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        return formatter.parse(formatter.format(new Date()));
    }*/
}

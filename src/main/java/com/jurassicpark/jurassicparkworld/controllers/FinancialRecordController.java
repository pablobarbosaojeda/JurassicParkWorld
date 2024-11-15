package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.models.FinancialRecord;
import com.jurassicpark.jurassicparkworld.models.Visitor;
import com.jurassicpark.jurassicparkworld.Repositories.ParkRepository;
import com.jurassicpark.jurassicparkworld.Repositories.VisitorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/financialRecords")
public class FinancialRecordController {

    @Autowired
    VisitorRepository visitorRepository;
    @Autowired
    ParkRepository parkRepository;
    @GetMapping
    public FinancialRecord  getfinancialRecordsOfPark(){
        List<Visitor> visitors = visitorRepository.findAll();
        Date currentDate = new Date();
        String dateShort = DateFormat.getDateInstance(DateFormat.SHORT).format(currentDate);

        FinancialRecord financialRecord = new FinancialRecord( dateShort ,0,visitors );
        return financialRecord;
}

}

package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.Repositories.PaddockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/paddocks")
public class PaddockController {

    @Autowired
    PaddockRepository paddockRepository;

}

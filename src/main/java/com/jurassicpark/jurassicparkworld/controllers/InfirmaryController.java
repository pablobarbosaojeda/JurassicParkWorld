package com.jurassicpark.jurassicparkworld.controllers;

import com.jurassicpark.jurassicparkworld.models.Infirmary;
import com.jurassicpark.jurassicparkworld.services.InfirmaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/infirmaries")
public class InfirmaryController {

    @Autowired
    private InfirmaryService infirmaryService;

    @GetMapping
    public List<Infirmary> getAllInfirmaries() {
        return infirmaryService.getAllInfirmaries();
    }

    @PatchMapping("/{dinosaurId}/heal")
    public ResponseEntity<?> healDinosaur(@PathVariable Long dinosaurId) {
        infirmaryService.healDinosaur(dinosaurId);
        return ResponseEntity.ok("Dinosaur healed successfully.");
    }
}

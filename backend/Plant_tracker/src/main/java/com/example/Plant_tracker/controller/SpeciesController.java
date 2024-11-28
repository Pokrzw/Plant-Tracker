package com.example.Plant_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Plant_tracker.service.SpeciesManager;
import com.example.Plant_tracker.models.Species;

import java.util.List;

@RestController
@RequestMapping("/species")
public class SpeciesController {
    
    @Autowired
    SpeciesManager speciesManager;

    @GetMapping("/species")
    public List<Species> getSpecies() {
        return speciesManager.getSpecies();
    }
}

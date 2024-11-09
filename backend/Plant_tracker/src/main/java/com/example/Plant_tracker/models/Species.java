package com.example.Plant_tracker.models;

// import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;




@Getter
@Setter
@AllArgsConstructor
public class Species {
  
    private int speciesId;
    private String name;
}

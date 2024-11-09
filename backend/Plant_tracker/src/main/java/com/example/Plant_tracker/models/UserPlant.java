package com.example.Plant_tracker.models;

// import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;


@Getter
@Setter
@AllArgsConstructor
public class UserPlant {

   
    private int id_plant;
    private String name; 
    private Species species;
    private LocalDateTime lastWatered;
    private LocalDateTime created;
    private ArrayList<Event> lastEvents;
    private ArrayList<Action> lastActions;

}

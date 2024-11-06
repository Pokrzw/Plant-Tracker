package com.example.Plant_tracker.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;



@Getter
@Setter
@AllArgsConstructor
public class UserPlant {

    private int id_plant;
    private String name;   
    private Species species;
    private LocalDateTime lastWatered;
    private LocalDateTime created;
    private HashMap<String, Event> lastEvents;
    private HashMap<String, Action> lastActions;

}

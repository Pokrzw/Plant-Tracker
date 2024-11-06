package com.example.Plant_tracker.models;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;



@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userId;
    private String name;
    private String password;
    private ArrayList <UserPlant> userPlants;
}

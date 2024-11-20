package com.example.Plant_tracker.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;




@Entity
@Getter
@Setter
@NoArgsConstructor
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    private String name;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL)  
    private List<UserPlant> userPlants;  
}

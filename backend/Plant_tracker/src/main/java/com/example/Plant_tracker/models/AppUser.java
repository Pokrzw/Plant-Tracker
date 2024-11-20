package com.example.Plant_tracker.models;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID (for numeric IDs)
    private Long id;
    private String name;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)  // One-to-many relationship
    private List<UserPlant> userPlants;  
}

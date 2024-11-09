// package com.example.Plant_tracker.modelsSQ;

// import jakarta.persistence.*;
// import java.util.List;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;



// @Getter
// @Setter
// @NoArgsConstructor
// @Entity
// public class User {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID (for numeric IDs)
//     private Long userId;
//     private String name;
//     private String password;

//     @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)  // One-to-many relationship
//     private List<UserPlant> userPlants;  
// }

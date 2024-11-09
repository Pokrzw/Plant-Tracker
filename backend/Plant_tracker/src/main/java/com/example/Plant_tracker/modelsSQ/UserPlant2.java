// package com.example.Plant_tracker.modelsSQ;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;

// import java.util.List;


// @Getter
// @Setter
// @NoArgsConstructor
// @Entity
// public class UserPlant {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID (for numeric IDs)
//     private Long id_plant;
//     private String name; 

//     // @ManyToOne
//     // @JoinColumn(name = "user_id")  // Ensure the column is properly joined
//     // private User user;  // Add this field for the 'user' reference


//     @ManyToOne  
//     private Species species;
//     private LocalDateTime lastWatered;
//     private LocalDateTime created;


//     // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)  // One-to-many relationship
//     // private List<Event> lastEvents;

//     // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//     // private List<Action> lastActions;

// }

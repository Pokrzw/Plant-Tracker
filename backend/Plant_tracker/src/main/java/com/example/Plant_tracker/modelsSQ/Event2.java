// package com.example.Plant_tracker.modelsSQ;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.NoArgsConstructor;


// @Entity
// @Getter
// @Setter
// @NoArgsConstructor
// public class Event {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID (for numeric IDs)
//     private Long idEvent;
//     private String type;
//     private LocalDateTime date;

//     @JoinColumn(name = "user_id")  // Ensure the column is properly joined
//     private User user;  // Add this field for the 'user' reference
// }

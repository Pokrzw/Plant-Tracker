package com.example.Plant_tracker.models;

// import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Getter
@Setter
@AllArgsConstructor
public class Action {
    
    private int idAction;
    private String type;
    private LocalDateTime date;


}

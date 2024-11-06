package com.example.Plant_tracker.models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;



@Getter
@Setter
@AllArgsConstructor
public class Action {
    private String idAction;
    private String type;
    private LocalDateTime date;
    
}

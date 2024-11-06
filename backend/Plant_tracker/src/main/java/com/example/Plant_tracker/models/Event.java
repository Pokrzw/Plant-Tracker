package com.example.Plant_tracker.models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;



@Getter
@Setter
@AllArgsConstructor
public class Event {
    private String idEvent;
    private String type;
    private LocalDateTime date;
}

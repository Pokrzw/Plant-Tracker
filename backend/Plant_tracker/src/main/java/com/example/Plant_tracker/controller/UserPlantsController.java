//Basic CRUD for users

package com.example.Plant_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Plant_tracker.server.CreateData;
import com.example.Plant_tracker.models.User;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserPlantsController {

    private final CreateData createdData;

    @Autowired
    public UserPlantsController(CreateData createdData) {
        this.createdData = createdData;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return createdData.createUsersWithPlants();
    }

}



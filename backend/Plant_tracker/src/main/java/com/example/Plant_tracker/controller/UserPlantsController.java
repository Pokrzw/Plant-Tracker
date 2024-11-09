//Basic CRUD for users

package com.example.Plant_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.Plant_tracker.server.CreateData;
import com.example.Plant_tracker.models.User;
import com.example.Plant_tracker.models.UserPlant;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserPlantsController {

    private final CreateData createdData;
    private final ArrayList<User> users;
    
    @Autowired
    public UserPlantsController(CreateData createdData) {
        this.createdData = createdData;
        this.users = createdData.createUsersWithPlants();
    }

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return users.get(id);
    }

    @GetMapping("/{id}/plants")
    public ArrayList<UserPlant> getUserAllPlants(@PathVariable int id) {
        return users.get(id).getUserPlants();
        
    }

    @PutMapping("/{userId}/plants/{plantId}")
    public ResponseEntity<String> updateUserPlant(
            @PathVariable int userId,
            @PathVariable int plantId,
            @RequestBody UserPlant updatedPlant) {

        // Check if the user exists
        if (userId < 0 || userId >= users.size()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        }

        ArrayList<UserPlant> plants = users.get(userId).getUserPlants();
        List<UserPlant> filteredPlant = plants.stream()
                .filter(plant -> plant.getId_plant() == plantId)
                .collect(Collectors.toList());

        if (!filteredPlant.isEmpty()) {
            UserPlant plant = filteredPlant.get(0);
            plant.setName(updatedPlant.getName());
            // plant.setSpecies(updatedPlant.getSpecies());
            plant.setLastWatered(updatedPlant.getLastWatered());
            plant.setCreated(updatedPlant.getCreated());
            return ResponseEntity.ok("Plant with ID " + plantId + " updated for user with ID " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant with ID " + plantId + " not found for user with ID " + userId);
        }
    }


    @DeleteMapping("/{id}/plants/{plantId}")
    public ResponseEntity<String> deleteUserPlantById(@PathVariable int id,@PathVariable int plantId ) {
        User user = users.get(id);
        if (user != null) {
            boolean removed = user.getUserPlants().removeIf(plant -> plant.getId_plant() == plantId);
            if (removed) {
                return ResponseEntity.ok("Plant with ID " + plantId + " deleted for user with ID " + id);
            } 
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");    
    }


}

//sortowanie po roślinach wymagających opieki
//kiedy jest niepodlewana od ddawwna
//szukanie pi gatunkach
//szukanie po nazwie
//moja roślinka ma sprawdzać, czy ostatnie podlanie było dawniej niż ww tej bazie danych
//


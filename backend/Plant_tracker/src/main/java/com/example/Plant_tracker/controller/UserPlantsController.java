//Basic CRUD for users

package com.example.Plant_tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Plant_tracker.service.UserPlantManager;
import com.example.Plant_tracker.service.SpeciesManager;

import com.example.Plant_tracker.models.AppUser;
import com.example.Plant_tracker.models.UserPlant;
import com.example.Plant_tracker.models.Species;


import java.util.List;


@RestController
@RequestMapping("/plants")
public class UserPlantsController {


    @Autowired
    UserPlantManager userPlantManager;

    @Autowired
    SpeciesManager speciesManager;

    @GetMapping
    public List<UserPlant> getAllPlants() {
        return userPlantManager.getAllPlants();
    }

    @GetMapping("/{userId}")
    public List<UserPlant> getAllUserPlants(@PathVariable Long userId) {
        return userPlantManager.getAllUserPlants(userId);
    }

    //Przykład: http://localhost:8080/filter/123/1,2,3
    @GetMapping("/filter/{userId}/{speciesNames}")
    public List<UserPlant> getFilteredUserPlants(
            @PathVariable Long userId,
            @PathVariable List<String> speciesNames) {
        List<Species> speciesEntities = speciesManager.getSpeciesByName(speciesNames);
        return userPlantManager.getFilteredUserPlantsBySpecies(userId, speciesEntities);
    }

    // @GetMapping("/{id}")
    // public User getUserById(@PathVariable int id) {
    //     return users.get(id);
    // }



    // @PutMapping("/{userId}/plants/{plantId}")
    // public ResponseEntity<String> updateUserPlant(
    //         @PathVariable int userId,
    //         @PathVariable int plantId,
    //         @RequestBody UserPlant updatedPlant) {

    //     // Check if the user exists
    //     if (userId < 0 || userId >= users.size()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
    //     }

    //     ArrayList<UserPlant> plants = users.get(userId).getUserPlants();
    //     List<UserPlant> filteredPlant = plants.stream()
    //             .filter(plant -> plant.getId_plant() == plantId)
    //             .collect(Collectors.toList());

    //     if (!filteredPlant.isEmpty()) {
    //         UserPlant plant = filteredPlant.get(0);
    //         plant.setName(updatedPlant.getName());
    //         // plant.setSpecies(updatedPlant.getSpecies());
    //         plant.setLastWatered(updatedPlant.getLastWatered());
    //         plant.setCreated(updatedPlant.getCreated());
    //         return ResponseEntity.ok("Plant with ID " + plantId + " updated for user with ID " + userId);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plant with ID " + plantId + " not found for user with ID " + userId);
    //     }
    // }


    // @DeleteMapping("/{id}/plants/{plantId}")
    // public ResponseEntity<String> deleteUserPlantById(@PathVariable int id,@PathVariable int plantId ) {
    //     User user = users.get(id);
    //     if (user != null) {
    //         boolean removed = user.getUserPlants().removeIf(plant -> plant.getId_plant() == plantId);
    //         if (removed) {
    //             return ResponseEntity.ok("Plant with ID " + plantId + " deleted for user with ID " + id);
    //         } 
    //     }
    //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found.");    
    // }


}

// sortowanie po roślinach wymagających opieki
// kiedy jest niepodlewana od ddawwna
// szukanie pi gatunkach
// szukanie po nazwie
// moja roślinka ma sprawdzać, czy ostatnie podlanie było dawniej niż ww tej bazie danych



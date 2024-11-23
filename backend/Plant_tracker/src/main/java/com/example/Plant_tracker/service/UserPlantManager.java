package com.example.Plant_tracker.service;

import org.springframework.stereotype.Service;

import com.example.Plant_tracker.repositories.UserPlantRepository;
import com.example.Plant_tracker.models.UserPlant;
import com.example.Plant_tracker.models.Species;
import com.example.Plant_tracker.models.AppUser;
import com.example.Plant_tracker.repositories.SpeciesRepository;
import com.example.Plant_tracker.repositories.AppUserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;




import java.util.List;
@NoArgsConstructor
@Service
public class UserPlantManager {
    
    UserPlantRepository userPlantRepository;
    AppUserRepository appUserRepository;
    SpeciesRepository speciesRepository;

    @Autowired
    public UserPlantManager(UserPlantRepository userPlantRepository, AppUserRepository appUserRepository, SpeciesRepository speciesRepository) {
        this.userPlantRepository = userPlantRepository;
        this.appUserRepository = appUserRepository;
        this.speciesRepository = speciesRepository;
    }


    //zwraca WSZYSTKIE rośliny
    public List<UserPlant> getAllPlants() {
        return this.userPlantRepository.findAll();
    }

    //zwraca rośliny danego użytkownika
    public List<UserPlant> getAllUserPlants(Long userId) {
        return this.userPlantRepository.findByUserId(userId);
    }

    //filtrowanie roślinek danego użytkownika po wszystkich wymienionych species
    public List<UserPlant> getFilteredUserPlantsBySpecies(Long userId, List<Species> filters) {
        return this.userPlantRepository.findByUserIdAndSpeciesIn(userId, filters);
        }

    //dodanie roślinki
    public boolean addPlantForUser(Long userId, UserPlant newPlant) {
        AppUser user = appUserRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Sprawdź, czy gatunek rośliny istnieje
        Species species = speciesRepository.findByName(newPlant.getSpecies().getName())
            .orElseThrow(() -> new RuntimeException("Species not found"));
            if (user != null && species != null) {            newPlant.setSpecies(species);
            newPlant.setUser(user);
    
            // Zapisz roślinę
            userPlantRepository.save(newPlant);
    
            // Dodaj roślinę do listy roślin użytkownika
            user.getUserPlants().add(newPlant);
            appUserRepository.save(user);
            return true;
        } 
        return false;
    }
    



    
    // public Species add(Species species) {
    //     return this.speciesRepository.save(species);
    // }


    //logowanie
    //sotowanie od daty dodania i od ostatniej daty podlania (porównianie z prawdziwymi danymi)
    //pole status
    //edycja rośliny nazwy i opis i gatunek wszystkiego
    //dodanie podlania z tej chwili teraz
    //podalanie wcześniejsze, gdzie można wybrać datę
    //dodanie gatunku
    //get 1 roślina by zwracała posortowane dane wg daty listy actions i events
}

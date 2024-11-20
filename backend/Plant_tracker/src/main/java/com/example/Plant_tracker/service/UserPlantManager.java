package com.example.Plant_tracker.service;

import org.springframework.stereotype.Service;

import com.example.Plant_tracker.repositories.UserPlantRepository;
import com.example.Plant_tracker.models.UserPlant;
import com.example.Plant_tracker.models.Species;



import java.util.List;

@Service
public class UserPlantManager {
    
    UserPlantRepository userPlantRepository;

    public UserPlantManager(UserPlantRepository userPlantRepository) {
        this.userPlantRepository = userPlantRepository;
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

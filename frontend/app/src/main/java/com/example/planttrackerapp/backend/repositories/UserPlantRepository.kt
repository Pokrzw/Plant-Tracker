package com.example.planttrackerapp.backend.repositories

import com.example.planttrackerapp.backend.dao.UserPlantDao
import com.example.planttrackerapp.backend.dao.SpeciesDao
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import java.util.Calendar


class UserPlantRepository(private val userPlantDao: UserPlantDao, private val speciesDao: SpeciesDao) {
//    val allUserPlants: List<Plant> = userPlantDao.getAll()

    suspend fun allUserPlants(): List<Plant> {
        return withContext(Dispatchers.IO) {
            userPlantDao.getAll()
        }
    }
    suspend fun insert(plant: Plant): Plant{
        return withContext(Dispatchers.IO) {
            userPlantDao.insert(plant)
            userPlantDao.getUserPlantById(plant.id)
        }
    }

    suspend fun deleteById(plantId: String) {
        withContext(Dispatchers.IO) {
            userPlantDao.deleteById(plantId)
        }
    }

    suspend fun getPlantById(id: String) {
        return withContext(Dispatchers.IO) {
            userPlantDao.getUserPlantById(id)
        }
    }

    suspend fun updateById(id: String, name: String, speciesName: String?) {
        withContext(Dispatchers.IO) {
            val species = speciesDao.getSpeciesByName(speciesName)
            if (species != null) {
                userPlantDao.updateById(id, name, speciesName, species)
            } else {
               Log.d("error-species", "spacies is null, updateById")
            }
        }
    }

    suspend fun updateNameById(id: String, name: String,) {
        withContext(Dispatchers.IO) {
            userPlantDao.updateNameById(id, name)
        }
    }

    suspend fun updateWateringHistory(plantId: String, newWaterHistory: List<Calendar>) {
        userPlantDao.updateWaterHistory(plantId, newWaterHistory)
    }

    suspend fun updateDiseaseHistory(plantId: String, newDiseaseHistory: List<Calendar>) {
        userPlantDao.updateDiseaseHistory(plantId, newDiseaseHistory)
    }

    suspend fun updateReplantHistory(plantId: String, newReplantHistory: List<Calendar>) {
        userPlantDao.updateReplantHistory(plantId, newReplantHistory)
    }

    suspend fun updateOtherActivitiesHistory(plantId: String, newOtherActivitiesHistory: List<Map<String,Calendar>>) {
        userPlantDao.updateOtherActivitiesHistory(plantId, newOtherActivitiesHistory)
    }


}
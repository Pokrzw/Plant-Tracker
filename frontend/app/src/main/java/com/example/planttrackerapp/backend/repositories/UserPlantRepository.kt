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
    suspend fun insert(plant: Plant): Plant?{
        try {
            if (plant.id.isBlank())
                throw IllegalStateException("Plant's id can't be empty")
            val plantExists = userPlantDao.getUserPlantById(plant.id)
            if (plantExists != null) {
                throw IllegalStateException("Plant's id is already in database")
            }
            return withContext(Dispatchers.IO) {
                userPlantDao.insert(plant)
                userPlantDao.getUserPlantById(plant.id)
            }
        } catch (e: IllegalStateException) {
            Log.e("Error message", "${e.message}")
        }
        return  null
    }

    suspend fun deleteById(plantId: String) {
        withContext(Dispatchers.IO) {
            userPlantDao.deleteById(plantId)
        }
    }

    suspend fun getPlantById(id: String): Plant {
        return withContext(Dispatchers.IO) {
            userPlantDao.getUserPlantById(id)
        }
    }

    suspend fun updateById(id: String, name: String, speciesName: String?, imageUri: String?) {
        withContext(Dispatchers.IO) {
            val species = speciesDao.getSpeciesByName(speciesName)
            if (species != null) {
                userPlantDao.updateById(id, name, speciesName, species, imageUri)
            } else if (userPlantDao.getUserPlantById(id)?.speciesName?.let {
                        speciesDao.getSpeciesByName(it) } != null) {
                val plant = userPlantDao.getUserPlantById(id)
                val plantSpecies = speciesDao.getSpeciesByName(plant.speciesName)
                userPlantDao.updateById(id, name, plant.speciesName, plantSpecies, imageUri)
            } else {
                Log.d("error-species", "species is null, updateById")
            }
        }
    }


            suspend fun updateNameById(id: String, name: String,) {
        withContext(Dispatchers.IO) {
            userPlantDao.updateNameById(id, name)
        }
    }

    suspend fun updateWateringHistory(plantId: String, newWaterHistory: List<Map<String?,Calendar>>) {
        userPlantDao.updateWaterHistory(plantId, newWaterHistory)
    }

    suspend fun updateDiseaseHistory(plantId: String, newDiseaseHistory: List<Map<String,Calendar>>) {
        userPlantDao.updateDiseaseHistory(plantId, newDiseaseHistory)
    }

    suspend fun updateRepotHistory(plantId: String, newRepotHistory: List<Map<String,Calendar>>) {
        userPlantDao.updateRepotHistory(plantId, newRepotHistory)
    }

    suspend fun updateOtherActivitiesHistory(plantId: String, newOtherActivitiesHistory: List<Map<String,Calendar>>) {
        userPlantDao.updateOtherActivitiesHistory(plantId, newOtherActivitiesHistory)
    }


}
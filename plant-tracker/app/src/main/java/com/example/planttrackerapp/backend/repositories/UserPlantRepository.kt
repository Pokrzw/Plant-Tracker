package com.example.planttrackerapp.backend.repositories

import com.example.planttrackerapp.backend.dao.UserPlantDao
import com.example.planttrackerapp.backend.dao.SpeciesDao
import com.example.planttrackerapp.model.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import java.util.Calendar


class UserPlantRepository(private val userPlantDao: UserPlantDao, private val speciesDao: SpeciesDao) {

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

    suspend fun updateById(id: String,
                           name: String,
                           speciesId: String?,
                           imageUri: String?) {
        try {
            withContext(Dispatchers.IO) {
                val plant = userPlantDao.getUserPlantById(id)
                val resolvedSpeciesId = speciesId ?:
                    plant.speciesId
                val species = speciesDao.getSpeciesById(
                    resolvedSpeciesId)
                if (species == null) {
                    throw IllegalStateException(
                        "No species given nor in database")
                }
                val resolvedImageUri = when {
                    imageUri.isNullOrEmpty() -> plant.imageUri
                    imageUri == "ClearImage" -> null
                    else -> imageUri
                }
                val updatedPlant = Plant(
                    id = plant.id,
                    name = name,
                    imageUri = resolvedImageUri,
                    speciesId = resolvedSpeciesId,
                    species = species,
                    waterHistory = plant.waterHistory,
                    created = plant.created,
                    diseaseHistory = plant.diseaseHistory,
                    repotHistory = plant.repotHistory,
                    otherActivitiesHistory = plant.otherActivitiesHistory,
                    qrCodeImage = plant.qrCodeImage
                )
                userPlantDao.updatePlant(updatedPlant)
            }
        } catch (e:IllegalStateException) {
            Log.e("error message","${e}")
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
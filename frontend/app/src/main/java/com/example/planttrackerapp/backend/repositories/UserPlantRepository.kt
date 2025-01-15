package com.example.planttrackerapp.backend.repositories

import com.example.planttrackerapp.backend.dao.UserPlantDao
import com.example.planttrackerapp.model.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserPlantRepository(private val userPlantDao: UserPlantDao) {
//    val allUserPlants: List<Plant> = userPlantDao.getAll()

    suspend fun allUserPlants(): List<Plant> {
        return withContext(Dispatchers.IO) {
            userPlantDao.getAll()
        }
    }
    suspend fun insert(plant: Plant) {
        return withContext(Dispatchers.IO) {
            userPlantDao.insert(plant)
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
}
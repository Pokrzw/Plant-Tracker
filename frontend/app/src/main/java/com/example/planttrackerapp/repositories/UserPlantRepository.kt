package com.example.planttrackerapp.repositories

import com.example.planttrackerapp.dao.UserPlantDao
import com.example.planttrackerapp.domain.UserPlant
import androidx.lifecycle.LiveData


class UserPlantRepository(private val userPlantDao: UserPlantDao) {
    val allUserPlants: LiveData<List<UserPlant>> = userPlantDao.getAll()

    suspend fun insert(plant: UserPlant) {
        userPlantDao.insert(plant)
    }

    suspend fun delete(plant: UserPlant) {
        userPlantDao.delete(plant)
    }

    suspend fun getPlantById(id: String) {
        userPlantDao.getUserPlantById(id)
    }
}
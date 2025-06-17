package com.example.planttrackerapp.backend.repositories

import com.example.planttrackerapp.backend.dao.FertilizerDao
import com.example.planttrackerapp.model.Fertilizer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FertilizerRepository(private val fertilizerDao: FertilizerDao) {
    suspend fun insertFertilizer(fertilizer: Fertilizer) {
        return withContext(Dispatchers.IO) {
            fertilizerDao.insert(fertilizer)
        }
    }

    suspend fun insertAll(fertilizers: List<Fertilizer>) {
        return withContext(Dispatchers.IO) {
            fertilizerDao.insertAll(fertilizers)
        }
    }

    suspend fun getFertilizerByName(name: String): Fertilizer {
        return withContext(Dispatchers.IO) {
            fertilizerDao.getFertilizerByName(name)
        }
    }

    suspend fun getAllFertilizer(): List<Fertilizer> {
        return withContext(Dispatchers.IO) {
            fertilizerDao.getAll()
        }
    }

    suspend fun updateFertilizerByName(name: String, newName: String) {
        return withContext(Dispatchers.IO) {
            fertilizerDao.updateFertilizer(name, newName)
        }
    }

    suspend fun deleteFertilizer(fertilizer: Fertilizer) {
        return withContext(Dispatchers.IO) {
            fertilizerDao.delete(fertilizer)
        }
    }
}
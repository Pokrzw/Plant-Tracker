package com.example.planttrackerapp.backend.repositories

import android.util.Log
import com.example.planttrackerapp.backend.dao.SpeciesDao
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SpeciesRepository(private val speciesDao: SpeciesDao) {
    suspend fun getAllSpecies(): List<Species> {
        return withContext(Dispatchers.IO) {
            speciesDao.getAll()
        }
    }

    suspend fun insertSpecies(species: Species) {
        return withContext(Dispatchers.IO) {
            speciesDao.insert(species)
        }
    }

    suspend fun getSpeciesByName(name: String): Species? {
        return withContext(Dispatchers.IO) {
            speciesDao.getSpeciesByName(name)
        }
    }

    suspend fun deleteSpecies(species: Species) {
        return withContext(Dispatchers.IO) {
            speciesDao.delete(species)
        }
    }

    suspend fun updateById(id: String,
                           name: String,
                           soilMoisture: Int) {
        try {
            withContext(Dispatchers.IO) {
                speciesDao.updateById(id, name, soilMoisture)
            }
        }
        catch (e:IllegalStateException) {
            Log.e("error message","${e}")
        }
    }
}
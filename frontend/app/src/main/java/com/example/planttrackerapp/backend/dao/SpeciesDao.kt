package com.example.planttrackerapp.backend.dao;

import androidx.room.*
import com.example.planttrackerapp.model.Species;

@Dao
interface SpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(species: Species)

    @Query("SELECT * FROM species WHERE name = :name")
    suspend fun getSpeciesByName(name: String?): Species

    @Query("SELECT * FROM species WHERE id = :id")
    suspend fun getSpeciesById(id: String?): Species

    @Query("SELECT * FROM species")
    suspend fun getAll(): List<Species>

    @Delete
    suspend fun delete(species: Species)

    @Query("DELETE FROM species")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(species: List<Species>)

    @Query("""
    UPDATE species
    SET
    name = :name,
    soilMoisture = :soilMoisture
    WHERE id = :id
    """)
    suspend fun updateById(
        id: String,
        name: String,
        soilMoisture: Int
    )
}

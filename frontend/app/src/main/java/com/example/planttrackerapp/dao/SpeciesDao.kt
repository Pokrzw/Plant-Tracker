package com.example.planttrackerapp.dao;

import androidx.room.*
import com.example.planttrackerapp.domain.Species;

@Dao
interface SpeciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(species: Species)

    @Query("SELECT * FROM species WHERE name = :name")
    suspend fun getSpeciesByName(name: String): Species?

    @Query("SELECT * FROM species")
    suspend fun getAll(): List<Species>
}

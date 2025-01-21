package com.example.planttrackerapp.backend.dao;

import androidx.room.*
import com.example.planttrackerapp.model.Plant;
import androidx.lifecycle.LiveData
import com.example.planttrackerapp.model.Species
import java.util.Calendar

@Dao
interface UserPlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPlant: Plant)

    @Query("SELECT * FROM user_plants WHERE id = :id")
    suspend fun getUserPlantById(id: String): Plant

    @Query("SELECT * FROM user_plants")
    fun getAll(): List<Plant>

    @Query("DELETE FROM user_plants WHERE id = :plantId")
    suspend fun deleteById(plantId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Plant>)

    @Query("""
    UPDATE user_plants 
    SET 
        name = :name,
        species_name = :speciesName,
        species = :species
    WHERE id = :id
""")
    suspend fun updateById(
        id: String,
        name: String,
        speciesName: String?,
        species: Species
    )

    @Query("""
    UPDATE user_plants 
    SET 
        name = :name
    WHERE id = :id
""")
    suspend fun updateNameById(
        id: String,
        name: String,
    )

    @Query("UPDATE user_plants SET waterHistory = :newWaterHistory WHERE id = :plantId")
    suspend fun updateWaterHistory(plantId: String, newWaterHistory: List<Calendar>)

}


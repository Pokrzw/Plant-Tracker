package com.example.planttrackerapp.dao;

import androidx.room.*
import com.example.planttrackerapp.model.Plant;
import androidx.lifecycle.LiveData

@Dao
interface UserPlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPlant: Plant)

    @Query("SELECT * FROM user_plants WHERE id = :id")
    suspend fun getUserPlantById(id: String): Plant?

    @Query("SELECT * FROM user_plants")
    fun getAll(): LiveData<List<Plant>>

    @Delete
    suspend fun delete(plant: Plant)
}

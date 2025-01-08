package com.example.planttrackerapp.dao;

import androidx.room.*
import com.example.planttrackerapp.domain.UserPlant;
import androidx.lifecycle.LiveData

@Dao
interface UserPlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPlant: UserPlant)

    @Query("SELECT * FROM user_plants WHERE id = :id")
    suspend fun getUserPlantById(id: String): UserPlant?

    @Query("SELECT * FROM user_plants")
    fun getAll(): LiveData<List<UserPlant>>

    @Delete
    suspend fun delete(plant: UserPlant)
}

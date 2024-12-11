package com.example.planttrackerapp.dao;

import androidx.room.*
import com.example.planttrackerapp.domain.UserPlant;

@Dao
interface UserPlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userPlant: UserPlant)

    @Query("SELECT * FROM user_plants WHERE id = :id")
    suspend fun getUserPlantById(id: String): UserPlant?

    @Delete
    suspend fun delete(userPlant: UserPlant)

    @Query("SELECT * FROM user_plants")
    suspend fun getAll(): List<UserPlant>
}

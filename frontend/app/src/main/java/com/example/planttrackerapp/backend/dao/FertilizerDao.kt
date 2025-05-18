package com.example.planttrackerapp.backend.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planttrackerapp.model.Fertilizer

@Dao
interface FertilizerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fertilizer: Fertilizer)

    @Query("SELECT * FROM fertilizer WHERE name = :name")
    suspend fun getFertilizerByName(name: String?): Fertilizer

    @Query("SELECT * FROM fertilizer")
    suspend fun getAll(): List<Fertilizer>

    @Delete
    suspend fun delete(fertilizer: Fertilizer)

    @Query("DELETE FROM fertilizer")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fertilizer: List<Fertilizer>)

    @Query("UPDATE fertilizer SET name = :newName WHERE name = :name")
    suspend fun updateFertilizer(name: String, newName: String)
}
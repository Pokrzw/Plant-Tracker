//package com.example.planttrackerapp.dao;
//
//
//import androidx.room.*
//import com.example.planttrackerapp.domain.Action;
//
//
//@Dao
//interface ActionDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(action: Action)
//
//    @Query("SELECT * FROM actions WHERE plant_id = :plantId")
//    suspend fun getActionsByPlantId(plantId: Long): List<Action>
//}

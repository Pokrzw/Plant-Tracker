//package com.example.planttrackerapp.dao;
//
//
//import androidx.room.*
//import com.example.planttrackerapp.domain.Event;
//
//@Dao
//interface EventDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(event: Event)
//
//    @Query("SELECT * FROM events WHERE plant_id = :plantId")
//    suspend fun getEventsByPlantId(plantId: Long): List<Event>
//}

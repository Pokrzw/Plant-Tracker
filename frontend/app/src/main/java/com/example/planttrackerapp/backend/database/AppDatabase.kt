package com.example.planttrackerapp.backend.database;

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import com.example.planttrackerapp.backend.dao.SpeciesDao
import com.example.planttrackerapp.backend.dao.UserPlantDao
//import com.example.planttrackerapp.domain.Event;
//import com.example.planttrackerapp.domain.Action;
import com.example.planttrackerapp.model.*;


@Database(
    entities = [Plant::class, Species::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userPlantDao(): UserPlantDao
    abstract fun speciesDao(): SpeciesDao
//    abstract fun eventDao(): EventDao
//    abstract fun actionDao(): ActionDao
}

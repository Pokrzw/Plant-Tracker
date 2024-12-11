package com.example.planttrackerapp.database;

import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import com.example.planttrackerapp.dao.*;
import com.example.planttrackerapp.domain.*;

@Database(
    entities = [UserPlant::class, Species::class, Event::class, Action::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userPlantDao(): UserPlantDao
    abstract fun speciesDao(): SpeciesDao
    abstract fun eventDao(): EventDao
    abstract fun actionDao(): ActionDao
}

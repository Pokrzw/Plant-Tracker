package com.example.planttrackerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "species")
data class Species(
    @PrimaryKey val name: String,
    val soilMoisture: Int
)

package com.example.planttrackerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "species")
data class Species(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val soilMoisture: Int
)

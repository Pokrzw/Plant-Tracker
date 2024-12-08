package com.example.planttrackerapp.domain;


import androidx.room.*

@Entity(tableName = "species")
data class Species(
    @PrimaryKey val name: String,
    val soilMoisture: Int
)

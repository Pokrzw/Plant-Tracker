package com.example.planttrackerapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fertilizer")
data class Fertilizer (
    @PrimaryKey val name: String
)
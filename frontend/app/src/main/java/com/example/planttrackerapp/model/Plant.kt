package com.example.planttrackerapp.model

// LocalDateTime nie działa dla API 24, dlatego narazie używam Calendar
// Może jeszcze zmienimy minimalne API na 26
// import java.time.LocalDateTime
import java.util.Calendar

data class Plant(
    val id: Int,
    val name: String,
    val species: Species,
    val waterHistory: List<Calendar>,
    val created: Calendar
)

package com.example.planttrackerapp.model

// LocalDateTime nie działa dla API 24, dlatego narazie używam Calendar
// Może jeszcze zmienimy minimalne API na 26
// import java.time.LocalDateTime
import java.util.Calendar
import androidx.room.*
import java.util.UUID


@Entity(
    tableName = "user_plants",
    foreignKeys = [
        ForeignKey(
            entity = com.example.planttrackerapp.domain.Species::class,
            parentColumns = ["name"],
            childColumns = ["species_name"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["species_name"])]
)
data class Plant(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    @ColumnInfo(name = "species_name") val speciesId: String? = null,
    val waterHistory: List<Calendar>,
    val created: Calendar
)

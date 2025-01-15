//package com.example.planttrackerapp.domain;
//
//
//import androidx.room.*
//import java.time.LocalDateTime
//import java.util.UUID
//
//
//@Entity(
//    tableName = "user_plants",
//    foreignKeys = [
//        ForeignKey(
//            entity = Species::class,
//            parentColumns = ["name"],
//            childColumns = ["species_name"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ],
//    indices = [Index(value = ["species_name"])]
//)
//data class UserPlant(
//    @PrimaryKey val id: String = UUID.randomUUID().toString(),
//    val name: String,
//    @ColumnInfo(name = "last_watered") val lastWatered: LocalDateTime? = null,
//    val created: LocalDateTime? = null,
//    @ColumnInfo(name = "species_name") val speciesId: String? = null
//)

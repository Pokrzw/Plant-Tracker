//package com.example.planttrackerapp.domain;
//
//
//import androidx.room.*
//import java.time.LocalDateTime
//
//@Entity(
//    tableName = "events",
//    foreignKeys = [
//        ForeignKey(
//            entity = UserPlant::class,
//            parentColumns = ["id"],
//            childColumns = ["plant_id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ],
//    indices = [Index(value = ["plant_id"])]
//)
//data class Event(
//    @PrimaryKey(autoGenerate = true) val idEvent: Long = 0,
//    val type: String,
//    val date: LocalDateTime,
//    @ColumnInfo(name = "plant_id") val plantId: Long
//)

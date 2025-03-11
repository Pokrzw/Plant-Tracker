package com.example.planttrackerapp.model

// LocalDateTime nie działa dla API 24, dlatego narazie używam Calendar
// Może jeszcze zmienimy minimalne API na 26
// import java.time.LocalDateTime
import java.util.Calendar
import androidx.room.*
import java.util.UUID
import com.example.planttrackerapp.backend.database.Converters
import com.example.planttrackerapp.backend.database.generateQRCodeAsBase64
import com.example.planttrackerapp.backend.database.generateQRCodeAsBase64


@Entity(
    tableName = "user_plants",
    foreignKeys = [
        ForeignKey(
            entity = Species::class,
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
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "species_name") val speciesName: String,
    @TypeConverters(Converters::class) val species: Species? = null,
    @TypeConverters(Converters::class) val waterHistory: List<Calendar>,
    val created: Calendar,
    @TypeConverters(Converters::class) val diseaseHistory: List<Calendar>,
    @TypeConverters(Converters::class) val replantHistory: List<Calendar>,
    @TypeConverters(Converters::class) val otherActivitiesHistory: List<Map<String,Calendar>>,
    val qrCodeImage: String? = generateQRCodeAsBase64(id)

)

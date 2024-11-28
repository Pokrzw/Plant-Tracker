package com.example.planttrackerapp.data

import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import java.util.Calendar

object Datasource {
    val agleonema = Species("Agleonema", 3)
    val monstera = Species("Monstera", 3)
    val filodendron = Species("Filodendron", 3)
    val hoya = Species("Hoya", 1)
    val dracena = Species("Dracena", 1)

    val speciesList = listOf(agleonema, monstera, filodendron, hoya, dracena)

    val plantList = listOf(
        Plant(
            name = "Agleonema",
            species = agleonema,
            lastWatered = getDate(2024, 11, 25, 10, 0),
            created = getDate(2024, 11, 14, 9, 0)
        ),
        Plant(
            name = "Monstera",
            species = monstera,
            lastWatered = getDate(2024, 11, 24, 11, 30), // 2024-11-24 11:30 AM
            created = getDate(2024, 10, 28, 8, 15) // 2024-10-28 8:15 AM
        ),
        Plant(
            name = "Filodendron",
            species = filodendron,
            lastWatered = getDate(2024, 11, 23, 14, 45), // 2024-11-23 2:45 PM
            created = getDate(2024, 10, 15, 10, 30) // 2024-10-15 10:30 AM
        ),
        Plant(
            name = "Hoya",
            species = hoya,
            lastWatered = getDate(2024, 11, 14, 16, 0), // 2024-11-14 4:00 PM
            created = getDate(2023, 11, 14, 9, 0) // 2023-11-14 9:00 AM
        ),
        Plant(
            name = "Dracena",
            species = dracena,
            lastWatered = getDate(2024, 11, 14, 16, 0), // 2024-11-14 4:00 PM
            created = getDate(2023, 11, 14, 9, 0) // 2023-11-14 9:00 AM
        )
    )

    // Funkcja pomocnicza do dodawania dat typu Calendar
    private fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Calendar {
        return Calendar.getInstance().apply {
            set(year, month - 1, day, hour, minute, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}

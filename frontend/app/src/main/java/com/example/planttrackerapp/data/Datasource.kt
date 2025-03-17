package com.example.planttrackerapp.data

import com.example.planttrackerapp.backend.database.DatabaseSeeder
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
            id = "0",
            name = "Agleonema",
            speciesName = "Agleonema",
            species = agleonema,
            waterHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            created = getDate(2024, 11, 14, 9, 0),
            diseaseHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            replantHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            otherActivitiesHistory = listOf(
                mapOf("Przycinanie" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Przeprowadzka" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Nawożenie" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Przycinanie" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Oprysk" to getDate(2024, 11, 5, 9, 45))

            ),
        ),
        Plant(
            id = "1",
            name = "Monstera",
            speciesName = "Monstera",
            species = monstera,
            waterHistory = listOf(
                getDate(2024, 11, 24, 11, 30),
                getDate(2024, 11, 19, 10, 0),
                getDate(2024, 11, 14, 9, 15),
                getDate(2024, 11, 9, 8, 45),
                getDate(2024, 11, 4, 7, 30)
            ),
            created = getDate(2024, 10, 28, 8, 15),
            diseaseHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            replantHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            otherActivitiesHistory = listOf(
                mapOf("Przycinanie" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Przeprowadzka" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Nawożenie" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Przycinanie" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Oprysk" to getDate(2024, 11, 5, 9, 45))

            ),

        ),
        Plant(
            id = "2",
            name = "Filodendron",
            speciesName = "Filodendron",
            species = filodendron,
            waterHistory = listOf(
                getDate(2024, 11, 23, 14, 45),
                getDate(2024, 11, 18, 13, 30),
                getDate(2024, 11, 13, 12, 15),
                getDate(2024, 11, 8, 11, 0),
                getDate(2024, 11, 3, 10, 45)
            ),
            created = getDate(2024, 10, 15, 10, 30),
            diseaseHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            replantHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            otherActivitiesHistory = listOf(
                mapOf("Przycinanie" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Przeprowadzka" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Nawożenie" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Przycinanie" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Oprysk" to getDate(2024, 11, 5, 9, 45))

            ),
        ),
        Plant(
            id = "3",
            name = "Hoya",
            speciesName = "Hoya",
            species = hoya,
            waterHistory = listOf(
                getDate(2024, 11, 14, 16, 0),
                getDate(2024, 11, 9, 15, 30),
                getDate(2024, 11, 4, 14, 45),
                getDate(2024, 10, 30, 13, 0),
                getDate(2024, 10, 25, 12, 15)
            ),
            created = getDate(2023, 11, 14, 9, 0),
            diseaseHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            replantHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            otherActivitiesHistory = listOf(
                mapOf("Przycinanie" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Przeprowadzka" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Nawożenie" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Przycinanie" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Oprysk" to getDate(2024, 11, 5, 9, 45))

            ),
        ),
        Plant(
            id = "4",
            name = "Dracena",
            speciesName = "Dracena",
            species = dracena,
            waterHistory = listOf(
                getDate(2024, 11, 14, 16, 0),
                getDate(2024, 11, 10, 15, 30),
                getDate(2024, 11, 6, 14, 45),
                getDate(2024, 11, 2, 13, 0),
                getDate(2024, 10, 29, 12, 15)
            ),
            created = getDate(2023, 11, 14, 9, 0),
            diseaseHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            replantHistory = listOf(
                getDate(2024, 11, 25, 10, 0),
                getDate(2024, 11, 20, 9, 0),
                getDate(2024, 11, 15, 8, 0),
                getDate(2024, 11, 10, 10, 30),
                getDate(2024, 11, 5, 9, 45)
            ),
            otherActivitiesHistory = listOf(
                mapOf("Przycinanie" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Przeprowadzka" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Nawożenie" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Przycinanie" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Oprysk" to getDate(2024, 11, 5, 9, 45))

            ),
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

package com.example.planttrackerapp.data

import com.example.planttrackerapp.backend.database.DatabaseSeeder
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import java.util.Calendar

object Datasource {
    val agleonema = Species(name = "Agleonema", soilMoisture = 3)
    val monstera = Species(name = "Monstera", soilMoisture = 3)
    val filodendron = Species(name = "Filodendron", soilMoisture =3)
    val hoya = Species(name = "Hoya", soilMoisture =1)
    val dracena = Species(name = "Dracena", soilMoisture =1)

    val speciesList = listOf(agleonema, monstera, filodendron, hoya, dracena)

    val plantList = listOf(
        Plant(
            id = "0",
            name = "Agleonema",
            speciesId = agleonema.id,
            species = agleonema,
            waterHistory = listOf(
                mapOf("z nazwozem" to getDate(2024, 11, 25, 10, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 20, 9, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 15, 8, 0)),
                mapOf("z nazwozem" to getDate(2024, 11, 10, 10, 30)),
                mapOf("z nazwozem" to getDate(2024, 11, 5, 9, 45))
            ),
            created = getDate(2024, 11, 14, 9, 0),
            diseaseHistory = listOf(
                mapOf("Poczerniałe liście" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Mszyce" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Poczerniałe liście" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Usychające liście" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Ślimaki" to getDate(2024, 11, 5, 9, 45))
            ),
            repotHistory = listOf(
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    25,
                    10,
                    0
                )
                ),
                mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(
                    2024,
                    11,
                    20,
                    9,
                    0
                )
                ),
                mapOf("Przesadzenie w czarniejszą glebę" to getDate(
                    2024,
                    11,
                    15,
                    8,
                    0
                )
                ),
                mapOf("Przesadzenie w mniejszą doniczkę" to getDate(
                    2024,
                    11,
                    10,
                    10,
                    30
                )
                ),
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    5,
                    9,
                    45
                )
                )
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
            speciesId = monstera.id,
            species = monstera,
            waterHistory = listOf(
                mapOf("z nazwozem" to getDate(2024, 11, 25, 10, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 20, 9, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 15, 8, 0)),
                mapOf("z nazwozem" to getDate(2024, 11, 10, 10, 30)),
                mapOf("z nazwozem" to getDate(2024, 11, 5, 9, 45))
            ),
            created = getDate(2024, 10, 28, 8, 15),
            diseaseHistory = listOf(
                mapOf("Poczerniałe liście" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Mszyce" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Poczerniałe liście" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Usychające liście" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Ślimaki" to getDate(2024, 11, 5, 9, 45))
            ),
            repotHistory = listOf(
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    25,
                    10,
                    0
                )
                ),
                mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(
                    2024,
                    11,
                    20,
                    9,
                    0
                )
                ),
                mapOf("Przesadzenie w czarniejszą glebę" to getDate(
                    2024,
                    11,
                    15,
                    8,
                    0
                )
                ),
                mapOf("Przesadzenie w mniejszą doniczkę" to getDate(
                    2024,
                    11,
                    10,
                    10,
                    30
                )
                ),
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    5,
                    9,
                    45
                )
                )
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
            speciesId = filodendron.id,
            species = filodendron,
            waterHistory = listOf(
                mapOf("z nazwozem" to getDate(2024, 11, 25, 10, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 20, 9, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 15, 8, 0)),
                mapOf("z nazwozem" to getDate(2024, 11, 10, 10, 30)),
                mapOf("z nazwozem" to getDate(2024, 11, 5, 9, 45))
            ),
            created = getDate(2024, 10, 15, 10, 30),
            diseaseHistory = listOf(
                mapOf("Poczerniałe liście" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Mszyce" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Poczerniałe liście" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Usychające liście" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Ślimaki" to getDate(2024, 11, 5, 9, 45))
            ),
            repotHistory = listOf(
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    25,
                    10,
                    0
                )
                ),
                mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(
                    2024,
                    11,
                    20,
                    9,
                    0
                )
                ),
                mapOf("Przesadzenie w czarniejszą glebę" to getDate(
                    2024,
                    11,
                    15,
                    8,
                    0
                )
                ),
                mapOf("Przesadzenie w mniejszą doniczkę" to getDate(
                    2024,
                    11,
                    10,
                    10,
                    30
                )
                ),
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    5,
                    9,
                    45
                )
                )
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
            speciesId = hoya.id,
            species = hoya,
            waterHistory = listOf(
                mapOf("z nazwozem" to getDate(2024, 11, 25, 10, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 20, 9, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 15, 8, 0)),
                mapOf("z nazwozem" to getDate(2024, 11, 10, 10, 30)),
                mapOf("z nazwozem" to getDate(2024, 11, 5, 9, 45))
            ),
            created = getDate(2023, 11, 14, 9, 0),
            diseaseHistory = listOf(
                mapOf("Poczerniałe liście" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Mszyce" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Poczerniałe liście" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Usychające liście" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Ślimaki" to getDate(2024, 11, 5, 9, 45))
            ),
            repotHistory = listOf(
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    25,
                    10,
                    0
                )
                ),
                mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(
                    2024,
                    11,
                    20,
                    9,
                    0
                )
                ),
                mapOf("Przesadzenie w czarniejszą glebę" to getDate(
                    2024,
                    11,
                    15,
                    8,
                    0
                )
                ),
                mapOf("Przesadzenie w mniejszą doniczkę" to getDate(
                    2024,
                    11,
                    10,
                    10,
                    30
                )
                ),
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    5,
                    9,
                    45
                )
                )
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
            speciesId = dracena.id,
            species = dracena,
            waterHistory = listOf(
                mapOf("z nazwozem" to getDate(2024, 11, 25, 10, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 20, 9, 0)),
                mapOf("bez nawozu" to getDate(2024, 11, 15, 8, 0)),
                mapOf("z nazwozem" to getDate(2024, 11, 10, 10, 30)),
                mapOf("z nazwozem" to getDate(2024, 11, 5, 9, 45))
            ),
            created = getDate(2023, 11, 14, 9, 0),
            diseaseHistory = listOf(
                mapOf("Poczerniałe liście" to getDate(2024, 11, 25, 10, 0)),
                mapOf("Mszyce" to getDate(2024, 11, 20, 9, 0)),
                mapOf("Poczerniałe liście" to getDate(2024, 11, 15, 8, 0)),
                mapOf("Usychające liście" to getDate(2024, 11, 10, 10, 30)),
                mapOf("Ślimaki" to getDate(2024, 11, 5, 9, 45))
            ),
            repotHistory = listOf(
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    25,
                    10,
                    0
                )
                ),
                mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(
                    2024,
                    11,
                    20,
                    9,
                    0
                )
                ),
                mapOf("Przesadzenie w czarniejszą glebę" to getDate(
                    2024,
                    11,
                    15,
                    8,
                    0
                )
                ),
                mapOf("Przesadzenie w mniejszą doniczkę" to getDate(
                    2024,
                    11,
                    10,
                    10,
                    30
                )
                ),
                mapOf("Przesadzenie w większą doniczkę" to getDate(
                    2024,
                    11,
                    5,
                    9,
                    45
                )
                )
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

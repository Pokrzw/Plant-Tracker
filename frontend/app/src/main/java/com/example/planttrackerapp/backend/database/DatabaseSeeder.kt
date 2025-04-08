package com.example.planttrackerapp.backend.database

import android.content.Context
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import android.util.Log
import com.example.planttrackerapp.data.Datasource
import kotlinx.coroutines.withContext

object DatabaseSeeder {

    private fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Calendar {
        return Calendar.getInstance().apply {
            set(year, month - 1, day, hour, minute, 0) // Miesiące są 0-indeksowane
            set(Calendar.MILLISECOND, 0)
        }
    }

    suspend fun seedDatabase(context: Context) {
        withContext(Dispatchers.IO) {
            val database = DatabaseProvider.getDatabase(context)
            val speciesDao = database.speciesDao()
            val userPlantDao = database.userPlantDao()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Dane do tabeli Species
                    val speciesList = listOf(
                        Species("Agleonema", 3),
                        Species("Monstera", 3),
                        Species("Filodendron", 3),
                        Species("Hoya", 1),
                        Species("Dracena", 1),
                        Species("Sylvari", 2),
                        Species("Croton", 3),
                        Species("Sanseveria", 1),
                        Species("Spiderwort", 1),
                        Species("Rhipsalis", 2),
                        Species("Ceropegia", 1),
                        Species("Pilea", 3),
                        Species("Spider plant", 3),
                        Species("Fern", 4),
                        Species("Epipremnum", 3),
                        Species("Marantha", 4),
                        Species("Orchidea", 3),
                        Species("Aloe vera", 2),
                        Species("Calatea", 4),
                        Species("Peace lily", 3),
                        Species("Banana", 3),
                        Species("Zamioculcas", 1),
                        Species("Stromanthe", 4),
                        Species("Philodendron", 4),
                        Species("Begonia", 4),
                        Species("Ficus", 3),
                        Species("Syngonium", 3),
                        Species("Peperomia", 3),
                        Species("Hoya", 2),
                        Species("Bamboo", 4),
                        Species("Palm", 3),
                        Species("Scindapsus", 3),
                        Species("Anturium", 3),
                        Species("Yuca", 3),
                        Species("Alocasia", 3),
                        Species("Oxalis", 3)
                    )

                    // Wstawienie danych do tabeli Species
                    speciesDao.insertAll(speciesList)

                    // Pobranie zapisanych obiektów Species z bazy danych
                    val speciesMap = speciesDao.getAll().associateBy { it.name }

                    Log.d("hi", "${speciesMap["Agleonema"]}")

                    val plantList = listOf(
                    Plant(
                        name = "Agleonema",
                        speciesName = "Agleonema",
                        species = speciesMap["Agleonema"],
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
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 25, 10, 0)),
                            mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(2024, 11, 20, 9, 0)),
                            mapOf("Przesadzenie w czarniejszą glebę" to getDate(2024, 11, 15, 8, 0)),
                            mapOf("Przesadzenie w mniejszą doniczkę" to getDate(2024, 11, 10, 10, 30)),
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 5, 9, 45))
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
                        name = "Monstera",
                        speciesName = "Monstera",
                        species = speciesMap["Monstera"],
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
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 25, 10, 0)),
                            mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(2024, 11, 20, 9, 0)),
                            mapOf("Przesadzenie w czarniejszą glebę" to getDate(2024, 11, 15, 8, 0)),
                            mapOf("Przesadzenie w mniejszą doniczkę" to getDate(2024, 11, 10, 10, 30)),
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 5, 9, 45))
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
                        name = "Filodendron",
                        speciesName = "Filodendron",
                        species = speciesMap["Filodendron"],
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
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 25, 10, 0)),
                            mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(2024, 11, 20, 9, 0)),
                            mapOf("Przesadzenie w czarniejszą glebę" to getDate(2024, 11, 15, 8, 0)),
                            mapOf("Przesadzenie w mniejszą doniczkę" to getDate(2024, 11, 10, 10, 30)),
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 5, 9, 45))
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
                        name = "Hoya",
                        speciesName = "Hoya",
                        species = speciesMap["Hoya"],
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
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 25, 10, 0)),
                            mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(2024, 11, 20, 9, 0)),
                            mapOf("Przesadzenie w czarniejszą glebę" to getDate(2024, 11, 15, 8, 0)),
                            mapOf("Przesadzenie w mniejszą doniczkę" to getDate(2024, 11, 10, 10, 30)),
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 5, 9, 45))
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
                        name = "Dracena",
                        speciesName = "Dracena",
                        species = speciesMap["Dracena"],
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
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 25, 10, 0)),
                            mapOf("Przesadzenie w ddoniczkę z 3 innymi kwiatkami" to getDate(2024, 11, 20, 9, 0)),
                            mapOf("Przesadzenie w czarniejszą glebę" to getDate(2024, 11, 15, 8, 0)),
                            mapOf("Przesadzenie w mniejszą doniczkę" to getDate(2024, 11, 10, 10, 30)),
                            mapOf("Przesadzenie w większą doniczkę" to getDate(2024, 11, 5, 9, 45))
                        ),
                        otherActivitiesHistory = listOf(
                            mapOf("Przycinanie" to getDate(2024, 11, 25, 10, 0)),
                            mapOf("Przeprowadzka" to getDate(2024, 11, 20, 9, 0)),
                            mapOf("Nawożenie" to getDate(2024, 11, 15, 8, 0)),
                            mapOf("Przycinanie" to getDate(2024, 11, 10, 10, 30)),
                            mapOf("Oprysk" to getDate(2024, 11, 5, 9, 45))

                        ),
                    ),

                )
                    plantList.forEach { a ->
                        Log.d("plant", "${a.species}")
                    }



                    // Wstawienie danych do tabeli Plant
               userPlantDao.insertAll(plantList)

                    // Logowanie powodzenia
                    Log.d("DatabaseSeeder", "Baza danych została wypełniona!")
                } catch (e: Exception) {
                    Log.e("DatabaseSeeder", "Błąd podczas wypełniania bazy danych: ${e.message}", e)
                }
            }
        }
    }
}

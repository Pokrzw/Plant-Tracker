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
            set(year, month - 1, day, hour, minute, 0) // Miesiące są od 0-indeksowane
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
                        Species(name = "Agleonema", soilMoisture = 3),
                        Species(name = "Monstera", soilMoisture = 3),
                        Species(name = "Filodendron", soilMoisture = 3),
                        Species(name = "Hoya", soilMoisture = 1),
                        Species(name = "Dracena", soilMoisture = 1),
                        Species(name = "Sylvari", soilMoisture = 2),
                        Species(name = "Croton", soilMoisture = 3),
                        Species(name = "Sanseveria", soilMoisture = 1),
                        Species(name = "Spiderwort", soilMoisture = 1),
                        Species(name = "Rhipsalis", soilMoisture = 2),
                        Species(name = "Ceropegia", soilMoisture = 1),
                        Species(name = "Pilea", soilMoisture = 3),
                        Species(name = "Spider plant", soilMoisture = 3),
                        Species(name = "Fern", soilMoisture = 4),
                        Species(name = "Epipremnum", soilMoisture = 3),
                        Species(name = "Marantha", soilMoisture = 4),
                        Species(name = "Orchidea", soilMoisture = 3),
                        Species(name = "Aloe vera", soilMoisture = 2),
                        Species(name = "Calatea", soilMoisture = 4),
                        Species(name = "Peace lily", soilMoisture = 3),
                        Species(name = "Banana", soilMoisture = 3),
                        Species(name = "Zamioculcas", soilMoisture = 1),
                        Species(name = "Stromanthe", soilMoisture = 4),
                        Species(name = "Philodendron", soilMoisture = 4),
                        Species(name = "Begonia", soilMoisture = 4),
                        Species(name = "Ficus", soilMoisture = 3),
                        Species(name = "Syngonium", soilMoisture = 3),
                        Species(name = "Peperomia", soilMoisture = 3),
                        Species(name = "Hoya", soilMoisture = 2),
                        Species(name = "Bamboo", soilMoisture = 4),
                        Species(name = "Palm", soilMoisture = 3),
                        Species(name = "Scindapsus", soilMoisture = 3),
                        Species(name = "Anturium", soilMoisture = 3),
                        Species(name = "Yuca", soilMoisture = 3),
                        Species(name = "Alocasia", soilMoisture = 3),
                        Species(name = "Oxalis", soilMoisture = 3)
                    )

                    speciesDao.insertAll(speciesList)

                    val speciesMap = speciesDao.getAll().associateBy { it.name }

                    val plantList = listOf(
                    Plant(
                        name = "Agleonema",
                        speciesId = speciesMap["Agleonema"]?.id ?: "default-id",
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
                        speciesId = speciesMap["Monstera"]?.id ?: "default-id",
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
                        speciesId = speciesMap["Filodendron"]?.id ?: "default-id",
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
                        speciesId = speciesMap["Hoya"]?.id ?: "default-id",
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
                        speciesId = speciesMap["Dracena"]?.id ?: "default-id",
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

               userPlantDao.insertAll(plantList)

                    Log.d("DatabaseSeeder", "Baza danych została wypełniona!")
                } catch (e: Exception) {
                    Log.e("DatabaseSeeder", "Błąd podczas wypełniania bazy danych: ${e.message}", e)
                }
            }
        }
    }
}

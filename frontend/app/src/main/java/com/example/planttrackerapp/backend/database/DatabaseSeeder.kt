package com.example.planttrackerapp.backend.database

import android.content.Context
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import android.util.Log
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
                        Species("Dracena", 1)
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
                            getDate(2024, 11, 25, 10, 0),
                            getDate(2024, 11, 20, 9, 0),
                            getDate(2024, 11, 15, 8, 0),
                            getDate(2024, 11, 10, 10, 30),
                            getDate(2024, 11, 5, 9, 45)
                        ),
                        created = getDate(2024, 11, 14, 9, 0)
                    ),
                    Plant(
                        name = "Monstera",
                        speciesName = "Monstera",
                        species = speciesMap["Monstera"],
                        waterHistory = listOf(
                            getDate(2024, 11, 24, 11, 30),
                            getDate(2024, 11, 19, 10, 0),
                            getDate(2024, 11, 14, 9, 15),
                            getDate(2024, 11, 9, 8, 45),
                            getDate(2024, 11, 4, 7, 30)
                        ),
                        created = getDate(2024, 10, 28, 8, 15)
                    ),
                    Plant(
                        name = "Filodendron",
                        speciesName = "Filodendron",
                        species = speciesMap["Filodendron"],
                        waterHistory = listOf(
                            getDate(2024, 11, 23, 14, 45),
                            getDate(2024, 11, 18, 13, 30),
                            getDate(2024, 11, 13, 12, 15),
                            getDate(2024, 11, 8, 11, 0),
                            getDate(2024, 11, 3, 10, 45)
                        ),
                        created = getDate(2024, 10, 15, 10, 30)
                    ),
                    Plant(
                        name = "Hoya",
                        speciesName = "Hoya",
                        species = speciesMap["Hoya"],
                        waterHistory = listOf(
                            getDate(2024, 11, 14, 16, 0),
                            getDate(2024, 11, 9, 15, 30),
                            getDate(2024, 11, 4, 14, 45),
                            getDate(2024, 10, 30, 13, 0),
                            getDate(2024, 10, 25, 12, 15)
                        ),
                        created = getDate(2023, 11, 14, 9, 0)
                    ),
                    Plant(
                        name = "Dracena",
                        speciesName = "Dracena",
                        species = speciesMap["Dracena"],
                        waterHistory = listOf(
                            getDate(2024, 11, 14, 16, 0),
                            getDate(2024, 11, 10, 15, 30),
                            getDate(2024, 11, 6, 14, 45),
                            getDate(2024, 11, 2, 13, 0),
                            getDate(2024, 10, 29, 12, 15)
                        ),
                        created = getDate(2023, 11, 14, 9, 0)
                    )
                )
                    plantList.forEach { a ->
                        Log.d("plant", "${a.species}")
                    }


                    // Wstawienie danych do tabeli Plant
               userPlantDao.insertAll(plantList)
               Log.d("plantLIST", "${userPlantDao.getAll()}")

                    // Logowanie powodzenia
                    Log.d("DatabaseSeeder", "Baza danych została wypełniona!")
                } catch (e: Exception) {
                    Log.e("DatabaseSeeder", "Błąd podczas wypełniania bazy danych: ${e.message}", e)
                }
            }
        }
    }
}

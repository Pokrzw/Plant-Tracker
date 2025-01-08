package com.example.planttrackerapp.database;

import android.util.Log
import com.example.planttrackerapp.domain.Species;
import com.example.planttrackerapp.domain.UserPlant;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime;

object DatabaseSeeder {

    private fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): LocalDateTime {
        return LocalDateTime.of(year, month, day, hour, minute)
    }

    fun seedDatabase(context: android.content.Context) {
        val database = DatabaseProvider.getDatabase(context)
        val speciesDao = database.speciesDao()
        val userPlantDao = database.userPlantDao()

        CoroutineScope(Dispatchers.IO).launch {
            if (speciesDao.getAll().isEmpty()) { // Dodaj dane tylko jeśli baza jest pusta
                speciesDao.insert(Species(name = "Ficus", soilMoisture = 2))
                speciesDao.insert(Species(name = "Monstera", soilMoisture = 3))
                speciesDao.insert(Species(name = "Dracena", soilMoisture = 1))
                speciesDao.insert(Species(name = "Hoya", soilMoisture = 1))
                speciesDao.insert(Species(name = "Filodendron", soilMoisture = 3))
                speciesDao.insert(Species(name = "Agleonema", soilMoisture = 3))
            }

            Log.d("MyTag", speciesDao.getAll().toString())

            val plantList = listOf(
                UserPlant(
                    name = "Agleonema",
                    lastWatered = getDate(2024, 11, 25, 10, 0),
                    created = getDate(2024, 11, 14, 9, 0),
                    speciesId = "Agleonema"
                ),
                UserPlant(
                    name = "Monstera",
                    lastWatered = getDate(2024, 11, 24, 11, 30),
                    created = getDate(2024, 10, 28, 8, 15),
                    speciesId = "Monstera"
                ),
                UserPlant(
                    name = "Filodendron",
                    lastWatered = getDate(2024, 11, 23, 14, 45),
                    created = getDate(2024, 10, 15, 10, 30),
                    speciesId = "Filodendron"
                ),
                UserPlant(
                    name = "Hoya",
                    lastWatered = getDate(2024, 11, 14, 16, 0),
                    created = getDate(2023, 11, 14, 9, 0),
                    speciesId = "Hoya"
                ),
                UserPlant(
                    name = "Dracena",
                    lastWatered = getDate(2024, 11, 14, 16, 0),
                    created = getDate(2023, 11, 14, 9, 0),
                    speciesId = "Dracena"
                )
            )

            // Wstawienie roślin użytkownika do bazy danych
            plantList.forEach { userPlant ->
                userPlantDao.insert(userPlant)
            }

            Log.d("MyTag", userPlantDao.getAll().toString())


        }
    }
}

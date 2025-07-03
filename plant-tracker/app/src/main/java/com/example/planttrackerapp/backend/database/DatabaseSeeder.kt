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

    suspend fun seedDatabase(context: Context) {
        withContext(Dispatchers.IO) {
            val database = DatabaseProvider.getDatabase(context)
            val speciesDao = database.speciesDao()

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

                    Log.d("DatabaseSeeder", "Baza danych została wypełniona!")
                } catch (e: Exception) {
                    Log.e("DatabaseSeeder", "Błąd podczas wypełniania bazy danych: ${e.message}", e)
                }
            }
        }
    }
}

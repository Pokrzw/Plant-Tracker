package com.example.planttrackerapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

//logi w konsoli
import android.util.Log

//wstrzyuje dane
import com.example.planttrackerapp.backend.database.DatabaseSeeder;

//Tag for logging
const val TAG = "MainActivity"

// Funkcja pomocnicza do sprawdzania, czy dane zostały załadowane
fun isDataAlreadySeeded(context: Context): Boolean {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("is_data_seeded", false)
}

// Funkcja do zapisania stanu, że dane zostały załadowane
fun markDataAsSeeded(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("is_data_seeded", true).apply()
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sprawdzamy, czy dane zostały już załadowane
        if (!isDataAlreadySeeded(this)) {
            // Jeśli dane nie zostały załadowane, uruchamiamy Seeder
            lifecycleScope.launch {
                DatabaseSeeder.seedDatabase(this@MainActivity)
                // Zmieniamy stan na załadowane dane
                markDataAsSeeded(this@MainActivity)
            }
        }

        enableEdgeToEdge()
        setContent {
            PlantTrackerAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlantApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



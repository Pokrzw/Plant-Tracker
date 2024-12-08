package com.example.planttrackerapp

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

//logi w konsoli
import android.util.Log

//wstrzyuje dane
import com.example.planttrackerapp.database.DatabaseSeeder;

//Tag for logging
const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseSeeder.seedDatabase(this)
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


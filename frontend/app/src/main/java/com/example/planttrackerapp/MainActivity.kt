package com.example.planttrackerapp

import androidx.room.Room
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

//splash screen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

//logi w konsoli
import android.util.Log
import com.example.planttrackerapp.backend.database.AppDatabase
import com.example.planttrackerapp.backend.database.DatabaseProvider

//wstrzykuje dane
import com.example.planttrackerapp.backend.database.DatabaseSeeder;

//Tag for logging
const val TAG = "MainActivity"



fun wasMigrationPerformed(context: Context,): Boolean {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//    Log.d("1", "${prefs}!")
    val previousVersion = prefs.getInt("db_version", -1)


    val db = DatabaseProvider.getDatabase(context)
    val currentVersion = db.openHelper.readableDatabase.version
    prefs.edit().putInt("db_version", currentVersion).apply()
//    Log.d("prev", "${previousVersion}!")

    return previousVersion != currentVersion
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)


        if (wasMigrationPerformed(this)) {
            lifecycleScope.launch {
                DatabaseSeeder.seedDatabase(this@MainActivity)
            }
//            Log.d("MigrationCheck", "Migracja była!")
        } else {
//            Log.d("MigrationCheck", "No migration!")
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




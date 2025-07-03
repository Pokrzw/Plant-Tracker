package com.example.planttrackerapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.planttrackerapp.backend.database.DatabaseProvider
import com.example.planttrackerapp.backend.database.DatabaseSeeder
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme
import kotlinx.coroutines.launch


const val TAG = "MainActivity"


fun wasMigrationPerformed(context: Context,): Boolean {
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val previousVersion = prefs.getInt("db_version", -1)


    val db = DatabaseProvider.getDatabase(context)
    val currentVersion = db.openHelper.readableDatabase.version
    prefs.edit().putInt("db_version", currentVersion).apply()

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




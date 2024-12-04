package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.model.Plant
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SinglePlantView(
    plant: Plant,
    onGoBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = plant.name,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Species: ${plant.species.name}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Soil Moisture Level: ${plant.species.soilMoisture}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Created On: ${formatDate(plant.created)}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Last Watered: ${formatDate(plant.lastWatered)}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Button(onClick = {
            // Tu będzie podlewanie
        }) {
            Text(text = "Water Plant")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onGoBack) {
            Text(text = "Back")
        }
    }
}

fun formatDate(calendar: java.util.Calendar): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter.format(calendar.time)
}

package com.example.planttrackerapp.ui

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planttrackerapp.PlantApp
import com.example.planttrackerapp.PlantAppScreen
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log

@Composable
fun SinglePlantView(
    plant: Plant?,
    onClickYes: (Int) -> Unit,
    onGoToForm: () -> Unit,
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPopUp by remember { mutableStateOf(false) }
    Log.d(TAG, "A single plant view")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = plant?.name ?: "",
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Species: ${plant?.species?.name ?: ""}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Soil Moisture Level: ${plant?.species?.soilMoisture ?: ""}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        val date = plant?.created
        val watered = plant?.lastWatered

        if(date != null && watered != null){
            Text(
                text = "Created On: ${formatDate(date)}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Last Watered: ${formatDate(watered)}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }

        Button(onClick = {
            // Tu będzie podlewanie
        }) {
            Text(text = "Water Plant")
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row {
            Button(onClick = onGoBack) {
                Text(text = "Back")
            }

            Button(onClick = onGoToForm) {
                Text(text = "Edit plant")
            }

            Button(onClick = {showPopUp = !showPopUp}) {
                Text(text = "Delete plant")
            }
        }
        if (showPopUp){
            AlertDialog(
                title = { Text(text = "Delete plant?") },
                onDismissRequest = {showPopUp = false},
                confirmButton = {
                    TextButton(
                        onClick = {
                            val id = plant?.id ?: -1
                            Log.d(TAG, "id: ${id}")
                            onClickYes(id)
                            showPopUp = false
                            onGoBack()
                        }
                    ) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(
                        onClick = {showPopUp = false}
                    ) { Text("No") }
                }
            )
        }
    }
}

fun formatDate(calendar: java.util.Calendar): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return formatter.format(calendar.time)
}

@Preview(showBackground = true)
@Composable
fun SinglePlantPreview(modifier: Modifier = Modifier) {
    PlantTrackerAppTheme {
        SinglePlantView(plant = Datasource.plantList[1], onGoBack = {}, onGoToForm = {}, onClickYes = {})
    }
}

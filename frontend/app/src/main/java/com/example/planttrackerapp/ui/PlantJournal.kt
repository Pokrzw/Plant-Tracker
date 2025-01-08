package com.example.planttrackerapp.ui

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.model.Plant
import java.util.Calendar
import java.util.Locale

@Composable
fun PlantJournal(
    plant: Plant?,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = plant?.name ?: "",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Watering History:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (plant?.waterHistory.isNullOrEmpty()) {
                item {
                    Text(
                        text = "No watering dates available.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                plant?.waterHistory?.sortedDescending()?.forEach { date ->
                    item {
                        WateringDateRow(date = date)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun WateringDateRow(date: Calendar, modifier: Modifier = Modifier) {
    val formattedDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault())
        .format(date.time)
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = formattedDate,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlantJournalPreview() {

}

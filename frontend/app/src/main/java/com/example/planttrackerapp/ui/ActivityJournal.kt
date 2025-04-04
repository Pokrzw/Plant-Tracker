package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.google.android.datatransport.runtime.synchronization.SynchronizationGuard.CriticalSection
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ActivityJournal(
    currentPlant: Plant? = null,
    onGoBack: () -> Unit,
    modifier: Modifier = Modifier
){

    var curSection by remember { mutableStateOf("Watering") }
    val sections = listOf("Watering", "Repotting", "Diseases", "Other")
    Column(
        modifier = modifier
    ) {
        HorizontalDivider(thickness = 2.dp)
        LazyRow(
            modifier = modifier,
        ) {
            items(sections.size){ element ->
                Button(
                    onClick = {
                        curSection = sections.get(element)
                    }
                ) {
                    Text(
                        text = "${sections.get(element)}"
                    )
                }
            }
        }
        Row{
            Column(Modifier.weight(1f)){
                Text("Date")

                val dateGetter = when (curSection){
                    "Watering" -> currentPlant?.waterHistory
                    "Repotting" -> currentPlant?.waterHistory
                    "Diseases" -> currentPlant?.waterHistory
                    "Other" -> currentPlant?.waterHistory
                    else -> currentPlant?.waterHistory
                }
                LazyColumn {
                    items(currentPlant?.waterHistory?.size ?: 0){index ->
//                        val waterHistoryStringified = formatDate(dateGetter?.get(index) ?: Calendar.getInstance())
                        val calendar = dateGetter?.get(index)?.values?.firstOrNull() ?: Calendar.getInstance()
                        val waterHistoryStringified = formatDate(calendar)
                        Text("${waterHistoryStringified}")
                    }
                }

            }
            Column(Modifier.weight(1f)){
                val sectionGetter = when (curSection){
                    "Watering" -> "Fertilizer"
                    "Repotting" -> "Pot size"
                    "Diseases" -> "Disease"
                    "Other" -> "Other"
                    else -> "Fertilizer"
                }
                Text(sectionGetter)
                LazyColumn {
                    
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityJournalPreview(modifier: Modifier = Modifier){
    ActivityJournal(currentPlant = Datasource.plantList[0], onGoBack = {})
}


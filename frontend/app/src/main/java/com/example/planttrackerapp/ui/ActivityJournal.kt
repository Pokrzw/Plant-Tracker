package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())

    val dateGetter = when (curSection){
        "Watering" -> currentPlant?.waterHistory
        "Repotting" -> currentPlant?.repotHistory
        "Diseases" -> currentPlant?.diseaseHistory
        "Other" -> currentPlant?.otherActivitiesHistory
        else -> currentPlant?.waterHistory
    }

    val sectionGetter = when (curSection){
        "Watering" -> "Fertilizer"
        "Repotting" -> "Pot size"
        "Diseases" -> "Disease"
        "Other" -> "Other"
        else -> "Fertilizer"
    }
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
                    },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = "${sections.get(element)}"
                    )
                }
            }
        }
        Column {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(1f)
                ,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Text("Date")
                Text(sectionGetter)
            }
            HorizontalDivider(thickness = 2.dp)


            Row {
                LazyColumn {
                    items(dateGetter?.size ?: 0){index ->
                        Row (
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceAround
                        ){
                            val calendar = dateGetter?.get(index)?.values?.firstOrNull() ?: Calendar.getInstance()
                            val historyStringified = formatter.format(calendar.time)
                            Text(text = "${historyStringified}",
                                 modifier = Modifier
                                     .fillMaxWidth(0.3f)
                                     .padding(4.dp)
                                )
                            Text(text = "${dateGetter?.get(index)?.keys?.first() ?: ""}",
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .padding(top = 4.dp, start = 12.dp, bottom = 4.dp, end = 4.dp)
                                )

                        }
                    }
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


package com.example.planttrackerapp.ui

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.planttrackerapp.backend.database.base64ToBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import coil3.compose.AsyncImage
import com.example.planttrackerapp.R
import kotlin.reflect.KFunction1
import androidx.core.net.toUri


@Composable
fun SinglePlantView(
    plant: Plant?,
    onClickYes: (String) -> Unit,
    onWater: KFunction1<String, Unit>,
    onGoToActivityJournal: () -> Unit,
    onGoToForm: () -> Unit,
    onGoBack: () -> Unit,
    onGoToRepot: () -> Unit,
    onGoToDisease: () -> Unit,
    onGoToOther: () -> Unit,
    modifier: Modifier = Modifier
) {

    Log.d(TAG, "SinglePlantView.kt")

    var fertilizer by remember { mutableStateOf("") }
    var showPopUp by remember { mutableStateOf(false) }
    var showWateredMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val plantImage = painterResource(R.drawable.imgbig)
            if (plant?.imageUri != null) {
                AsyncImage(
                    model = plant.imageUri.toUri(),
                    contentDescription = plant.imageUri,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            } else {
                Image(
                    painter = plantImage,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant?.name ?: "",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = plant?.species?.name ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Button(onClick = onGoToActivityJournal) {
                        Text("Journal")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = onGoToForm) {
                        Text("Edit")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Recommended soil moisture level: ${plant?.species?.soilMoisture ?: ""}",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        val date = plant?.created
        val watered = plant?.waterHistory
            ?.flatMap { it.values }
            ?.maxByOrNull { it.timeInMillis}

        if (date != null) {
            Text(
                text = "Created: ${formatDate(date)}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (watered != null) {
            Text(
                text = "Last watered: ${formatDate(watered)}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            TextField(
                value = fertilizer,
                onValueChange = {
                    fertilizer = it
                },
                label = {Text("Add fertilizer")},
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 8.dp, end = 16.dp)
                    .fillMaxWidth(0.4f)
            )
            Button(onClick = {
                showWateredMessage = true
                onWater(fertilizer)
                fertilizer = ""
            }) {
                Text(text = "Water plant")
            }

                Spacer(modifier = Modifier.width(8.dp))

                AnimatedVisibility(
                    visible = showWateredMessage,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { -it / 8 }),
                    exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -it / 8 })
                ) {
                    Text(
                        text = "Plant watered!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }

                if (showWateredMessage) {
                    LaunchedEffect(Unit) {
                        delay(2000) // 2 seconds
                        showWateredMessage = false
                    }
                }
            }
            Button(
                onClick = {onGoToRepot()}
            ) {
                Text("Note repot")
            }
            Button(
                onClick = {onGoToDisease()}
            ) {
                Text("Note disease")
            }
            Button(
                onClick = {onGoToOther()}
            ) {
                Text("Note other event")
            }

            Button(onClick = { showPopUp = !showPopUp }) {
                Text(text = "Delete plant")
            }
        }



        if (showPopUp) {
            AlertDialog(
                title = { Text(text = "Delete plant?") },
                onDismissRequest = { showPopUp = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val id = plant?.id ?: "-1"
                            onClickYes(id)
                            showPopUp = false
                            onGoBack()
                        }
                    ) { Text("Yes") }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showPopUp = false }
                    ) { Text("No") }
                }
            )
        }

    }



fun formatDate(calendar: java.util.Calendar): String {
    val formatter = SimpleDateFormat("HH:mm, MMM dd yyyy", Locale.getDefault())
    return formatter.format(calendar.time)
}

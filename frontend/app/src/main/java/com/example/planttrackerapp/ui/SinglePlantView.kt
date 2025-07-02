package com.example.planttrackerapp.ui

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.planttrackerapp.R
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.model.Plant
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.reflect.KFunction1


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
            ?.maxByOrNull { it.timeInMillis }

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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = fertilizer,
                onValueChange = {
                    fertilizer = it
                },
                label = { Text("Fertilizer (optional)") },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    showWateredMessage = true
                    onWater(fertilizer)
                    fertilizer = ""
                },
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text(text = "Water plant")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp) // reserve space
                    .padding(top = 8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = showWateredMessage,
                    enter = slideInVertically(initialOffsetY = { -it / 2 }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it / 2 }) + fadeOut()
                ) {
                    Text(
                        text = "Plant watered!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }


            if (showWateredMessage) {
                LaunchedEffect(Unit) {
                    delay(2000)
                    showWateredMessage = false
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "${plant?.name}'s events",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Button(
                    onClick = { onGoToRepot() },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 4.dp)
                ) {
                    Text("Note repot")
                }

                Button(
                    onClick = { onGoToDisease() },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 4.dp)
                ) {
                    Text("Note disease")
                }

                Button(
                    onClick = { onGoToOther() },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 4.dp)
                ) {
                    Text("Note something else")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showPopUp = !showPopUp },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Delete plant")
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
    }



fun formatDate(calendar: java.util.Calendar): String {
    val formatter = SimpleDateFormat("HH:mm, MMM dd yyyy", Locale.getDefault())
    return formatter.format(calendar.time)
}

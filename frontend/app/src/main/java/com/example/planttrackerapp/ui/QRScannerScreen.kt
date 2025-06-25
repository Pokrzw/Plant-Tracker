package com.example.planttrackerapp.ui


import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planttrackerapp.model.Plant
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlin.reflect.KFunction1

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen(
    setPlantOnScan: (Plant) -> Unit,
    formViewModel: FormViewModel,
    onWater: KFunction1<String, Unit>,
    onClickDetails: (Plant) -> Unit
) {
    val cameraPermission = Manifest.permission.CAMERA
    val permissionState = rememberPermissionState(cameraPermission)
    var scannedPlant by remember { mutableStateOf<Plant?>(null) }
    var scannedCode by remember { mutableStateOf<String?>(null) }
    var showNotFoundMessage by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(showNotFoundMessage) {
        if (showNotFoundMessage) {
            delay(3000)
            showNotFoundMessage = false
        }
    }

    if (permissionState.status.isGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            QRCodeScanner { qrCode ->
                scannedCode = qrCode
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showNotFoundMessage,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "No plant found.",
                        color = Color.Red,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Button(
                    onClick = {
                        scannedCode?.let { code ->
                            val plant = formViewModel.getPlantById(code)
                            if (plant != null) {
                                scannedPlant = plant
                                setPlantOnScan(plant)
                                isBottomSheetVisible = true
                            } else {
                                showNotFoundMessage = true
                            }
                        }
                    },
                    enabled = scannedCode != null,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text("Scan QR Code")
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Camera permission is required to scan QR codes.")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Grant Permission")
            }
        }
    }

    if (scannedPlant != null && isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            BottomSheetContent(scannedPlant!!, onWater, onClickDetails)
        }
    }
}


@Composable
fun BottomSheetContent(plant: Plant, onWater: KFunction1<String, Unit>, onClickDetails: (Plant) -> Unit) {
    var fertilizer by remember { mutableStateOf("") }
    var showWateredMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Species: ${plant.species?.name ?: "unknown"}",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = { onClickDetails(plant) }) {
                Text("Details")
            }
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
    }
}

package com.example.planttrackerapp.ui


import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planttrackerapp.model.Plant
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen(
    setPlantOnScan: (Plant) -> Unit,
    formViewModel: FormViewModel,
    onWater: () -> Unit,
    onClickDetails: (Plant) -> Unit
) {
    var scannedPlant by remember { mutableStateOf<Plant?>(null) }
    var showNotFoundMessage by remember { mutableStateOf(false) }
    var showWateredMessage by remember { mutableStateOf(false) }
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(showNotFoundMessage) {
        if (showNotFoundMessage) {
            delay(3000) // Show message for 3 seconds
            showNotFoundMessage = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (permissionState.status.isGranted) {
            QRCodeScanner { qrCode ->
                val plant = formViewModel.getPlantById(qrCode)
                if (plant != null) {
                    scannedPlant = plant
                    setPlantOnScan(plant)
                } else {
                    showNotFoundMessage = true
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Camera permission is required to scan QR codes.",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }

        // Show "Plant not found" overlay if no plant is found
        if (showNotFoundMessage) {
            AnimatedVisibility(
                visible = showNotFoundMessage,
                exit = fadeOut(animationSpec = tween(durationMillis = 2000)) // Smooth fade-out in 2 sec
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Plant not found.",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

// Bottom sheet to show plant details when a plant is found
    scannedPlant?.let { plant ->
        ModalBottomSheet(
            onDismissRequest = { scannedPlant = null },
            sheetState = sheetState,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = plant.name,
                    fontSize = 18.sp
                )
                plant.species?.let {
                    Text(
                        text = "Species: ${it.name ?: ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showWateredMessage = true
                            onWater()
                        }
                    ) {
                        Text(text = "Water plant")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onClickDetails(plant)
                    }
                ) {
                    Text("Profile")
                }
            }
        }
    }
}


package com.example.planttrackerapp.data

import android.net.Uri
import com.example.planttrackerapp.model.Plant

data class PlantUiState(
    val currentlyEditedPlant: Plant? = null,
)
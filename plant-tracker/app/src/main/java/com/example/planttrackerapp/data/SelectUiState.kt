package com.example.planttrackerapp.data

import android.content.Context
import com.example.planttrackerapp.model.Plant

data class SelectUiState(
    val selectedPlantList: List<Plant> = listOf(),
    val context: Context? = null
)
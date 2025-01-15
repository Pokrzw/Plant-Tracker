package com.example.planttrackerapp.data

import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import java.util.Calendar

data class FormUiState(
    val id: String = "",
    val name: String = "",
    val species: Species? = null,
    val waterHistory: List<Calendar>? = emptyList(),
    val created: Calendar? = null,
    val speciesList: List<Species> = emptyList(),
    val plantsList: List<Plant> = emptyList()
)
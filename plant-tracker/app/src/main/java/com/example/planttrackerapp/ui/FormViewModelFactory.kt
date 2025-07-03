package com.example.planttrackerapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import com.example.planttrackerapp.backend.repositories.SpeciesRepository


class FormViewModelFactory(
    private val userPlantRepository: UserPlantRepository,
    private val speciesRepository: SpeciesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormViewModel(userPlantRepository, speciesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

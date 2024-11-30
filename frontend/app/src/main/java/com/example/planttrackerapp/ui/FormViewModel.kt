package com.example.planttrackerapp.ui


import androidx.lifecycle.ViewModel
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.data.FormUiState
import com.example.planttrackerapp.model.Species
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Date

class FormViewModel: ViewModel() {

    private val _formUiState = MutableStateFlow(FormUiState())
    val formUiState: StateFlow<FormUiState> = _formUiState.asStateFlow()

    fun setId(){
        _formUiState.update { currentState ->
            currentState.copy(
                id = Datasource.idNumber
            )
        }
        Datasource.idNumber++
    }
    fun setName(name: String){
        _formUiState.update { currentState ->
            currentState.copy(
                name = name
            )
        }
    }
    
    fun setSpecies(species: Species){
        _formUiState.update { currentState -> 
            currentState.copy(
                species = species
            )
        }
    }

    fun setLastWatered(date: Calendar){
        _formUiState.update { currentState ->
            currentState.copy(
                lastWatered = date
            )
        }
    }

    fun setCreated(date: Calendar){
        _formUiState.update { currentState ->
            currentState.copy(
                created = date
            )
        }
    }

    fun resetForm(){
        _formUiState.value = FormUiState()
    }
}
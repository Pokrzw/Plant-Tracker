package com.example.planttrackerapp.ui


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.data.FormUiState
import com.example.planttrackerapp.data.PlantUiState
import com.example.planttrackerapp.model.Plant
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

    private val _plantUiState = MutableStateFlow(PlantUiState())
    val plantUiState: StateFlow<PlantUiState> = _plantUiState.asStateFlow()

    init {
        populateUiState()
    }

    fun populateUiState(){
        val speciesList = Datasource.speciesList
        val plantList = Datasource.plantList
        val baseId = plantList.size
        _formUiState.value = FormUiState(id = baseId, speciesList = speciesList, plantsList = plantList)
        _plantUiState.value = PlantUiState(currentlyEditedPlant = null)
    }

    fun onSetPlant(plant: Plant){
        _plantUiState.update { currentState ->
            currentState.copy(
                currentlyEditedPlant = plant
            )
        }
    }

    fun onDeletePlant(id: Int){
        val newPlantList = _formUiState.value.plantsList.filter { it.id != id }
        _formUiState.update { currentState ->
            currentState.copy(
                plantsList = newPlantList
            )
        }
    }
    fun onClickUpdate(){
        val id = _plantUiState.value.currentlyEditedPlant?.id ?: -1
        val formName = _formUiState.value.name
        val formSpecies = _formUiState.value.species
        val plantName = _plantUiState.value.currentlyEditedPlant?.name ?: ""
        val plantSpecies = _plantUiState.value.currentlyEditedPlant?.species

        Log.d(TAG, "ONCLICKUPDATE id: ${id}")
        val name =
            if(formName.equals("") && !plantName.equals("")) plantName
            else formName

        //!!!! DO ZMIANY!!!!
        val species =
            if(plantSpecies!=null && formSpecies==null) plantSpecies
            else if (formSpecies!= null) formSpecies
            else Datasource.speciesList[0]

        val plantList = _formUiState.value.plantsList
        val searchedElement = plantList.filter { it.id == id}[0]
        val searchedElementId = plantList.indexOf(searchedElement)
        if (searchedElementId!=-1){
            val searchedElementCopy = searchedElement.copy(name = name, species = species)
            val copyOfPlantList = plantList.map {
                if (it.id == searchedElementId) searchedElementCopy
                else it
            }
            _formUiState.update { currentState ->
                currentState.copy(
                    plantsList = copyOfPlantList
                )
            }
        }


    }

    fun onClickAdd(){
        val id = _formUiState.value.id
        val name = _formUiState.value.name
        val species = _formUiState.value.species
        val lastWatered = Calendar.getInstance()
        if(species!=null){
            val plant = Plant(id, name, species, lastWatered, lastWatered)
            _formUiState.update { currentState ->
                currentState.copy(
                    plantsList = currentState.plantsList.plus(plant)
                )
            }
            resetForm()
        }
        Log.d(TAG, "Aktualne rosliny: ${_formUiState.value.plantsList.joinToString("\n") }}}")
    }
    fun saveNameOnUpdate(name: String?){
        val name = name ?: _formUiState.value.name
        _formUiState.update { currentState ->
            currentState.copy(
                name = name
            )
        }
//        Log.d(TAG, "")
    }
    fun saveSpeciesOnUpdate(species: Species?){
        _formUiState.update { currentState ->
            currentState.copy(
                species = species
            )
        }
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
        _formUiState.update { currentState ->
            currentState.copy(
                id = currentState.id.inc(),
                name = "",
                species = null
            )
        }
    }
}
package com.example.planttrackerapp.ui


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
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
import java.util.UUID
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import androidx.lifecycle.viewModelScope
import com.example.planttrackerapp.backend.repositories.SpeciesRepository
import com.example.planttrackerapp.data.SelectUiState
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream


class FormViewModel(
    private val plantsRepository: UserPlantRepository,
    private val speciesRepository: SpeciesRepository
    ): ViewModel() {

    private val _formUiState = MutableStateFlow(FormUiState())
    val formUiState: StateFlow<FormUiState> = _formUiState.asStateFlow()

    private val _plantUiState = MutableStateFlow(PlantUiState())
    val plantUiState: StateFlow<PlantUiState> = _plantUiState.asStateFlow()

    private val _selectUiState = MutableStateFlow(SelectUiState())
    val selectUiState: StateFlow<SelectUiState> = _selectUiState.asStateFlow()


    init {
        populateUiState()
    }

    fun saveActionForm(action: String, option: String) {
        val currentlyEditedPlant = _plantUiState.value.currentlyEditedPlant

        val newMap = mapOf(action to Calendar.getInstance())

        if (currentlyEditedPlant != null) {
            val updatedPlant =  when(option){
                "repot" -> {
                    currentlyEditedPlant.copy(
                        repotHistory =  currentlyEditedPlant.repotHistory + newMap
                    )
                }
                "disease" -> {
                    currentlyEditedPlant.copy(
                        diseaseHistory =  currentlyEditedPlant.diseaseHistory + newMap
                    )
                }

              else -> {
                  currentlyEditedPlant.copy(
                  otherActivitiesHistory =  currentlyEditedPlant.otherActivitiesHistory + newMap
              )}
          }


            val updatedPlantsList = _formUiState.value.plantsList.map {
                if (it.id == updatedPlant.id) updatedPlant else it
            }

            when(option){
                "repot" -> {
                    viewModelScope.launch {
                        plantsRepository.updateRepotHistory(currentlyEditedPlant.id, updatedPlant.repotHistory)
                    }
                }
                "disease" -> {
                    viewModelScope.launch {
                        plantsRepository.updateDiseaseHistory(currentlyEditedPlant.id, updatedPlant.repotHistory)
                    }
                }
                else -> {
                    viewModelScope.launch {
                        plantsRepository.updateOtherActivitiesHistory(currentlyEditedPlant.id, updatedPlant.repotHistory)
                    }
                }
            }


            _formUiState.update { currentState ->
                currentState.copy(plantsList = updatedPlantsList)
            }

            _plantUiState.update { currentState ->
                currentState.copy(currentlyEditedPlant = updatedPlant)
            }
        } else {
            Log.d(TAG, "Brak edytowanej rośliny.")
        }
    }

    fun saveSelection(plantList: List<Plant>){
        _selectUiState.update { currentState ->
            currentState.copy(
                selectedPlantList = plantList
            )
        }
    }

    fun populateUiState(){
        viewModelScope.launch {
            val speciesList = withContext(Dispatchers.IO) { speciesRepository.getAllSpecies() }
            val plantList = withContext(Dispatchers.IO) { plantsRepository.allUserPlants() }
            val baseId = UUID.randomUUID().toString()
            _formUiState.value = FormUiState(id = baseId, speciesList = speciesList, plantsList = plantList)
            _plantUiState.value = PlantUiState(currentlyEditedPlant = null)
        }
    }

    fun onSetPlant(plant: Plant){
        _plantUiState.update { currentState ->
            currentState.copy(
                currentlyEditedPlant = plant
            )
        }
    }

    fun getPlantById(id: String): Plant? {
        return _formUiState.value.plantsList.find { it.id == id }
    }

    fun onDeletePlant(id: String){
        val newPlantList = _formUiState.value.plantsList.filter { it.id != id }
        _formUiState.update { currentState ->
            currentState.copy(
                plantsList = newPlantList
            )
        }

        viewModelScope.launch {
            plantsRepository.deleteById(id)
            populateUiState()
        }

    }

    fun onClickUpdate(){
        val id = _plantUiState.value.currentlyEditedPlant?.id ?: -1
        val formUri = _formUiState.value.imgUri
        val plantUri = _plantUiState.value.currentlyEditedPlant?.imageUri
        val formName = _formUiState.value.name
        val formSpecies = _formUiState.value.species
        val plantName = _plantUiState.value.currentlyEditedPlant?.name ?: ""
        val plantSpecies = _plantUiState.value.currentlyEditedPlant?.species

        val name =
            if(formName.equals("") && !plantName.equals("")) plantName
            else formName

        Log.d(TAG, "ONCLICKUPDATE formUri: ${formUri}")
        Log.d(TAG, "ONCLICKUPDATE plantUri: ${plantUri}")
//        val uri =
//            if (plantUri != null && formUri==null) plantUri
//            else if (formUri!=null ) formUri.toString()
//            else null

        val uri = formUri
        //!!!! DO ZMIANY!!!!
        val species =
            if(plantSpecies!=null && formSpecies==null) plantSpecies
            else if (formSpecies!= null) formSpecies
            else Datasource.speciesList[0]

        val plantList = _formUiState.value.plantsList
        val searchedElement = plantList.filter { it.id == id}[0]
        val searchedElementId = searchedElement.id
//        val searchedElementId = plantList.indexOf(searchedElement)
//        if (searchedElementId!=-1){
            val searchedElementCopy = searchedElement.copy(name = name, species = species, imageUri = uri?.toString())
        
            viewModelScope.launch {
                Log.d("BEFORE UPDATE IMAGE URI", "${searchedElementCopy.imageUri}, ${searchedElementCopy.id}")
                plantsRepository.updateById(searchedElementCopy.id, name, formSpecies?.name, searchedElementCopy.imageUri)
                val plantList = withContext(Dispatchers.IO) { plantsRepository.allUserPlants() }
                plantList.forEach{plant -> Log.d("AFTER UPDATE IMAGE URI", "${plant.imageUri}")}
            }
            val copyOfPlantList = plantList.map {
                if (it.id == searchedElementId) searchedElementCopy
                else it
            }
            _formUiState.update { currentState ->
                currentState.copy(
                    plantsList = copyOfPlantList
                )
            }

            // ustawienie currentlyEditedPlant na tę z nowyymi danymi
            _plantUiState.update { currentState ->
                currentState.copy(currentlyEditedPlant = searchedElementCopy)
            }
//        }


    }

    fun onClickAdd(onSuccess: () -> Unit) {
        val id = _formUiState.value.id
        val name = _formUiState.value.name
        val species = _formUiState.value.species
        val currentDate = Calendar.getInstance()
        val curImg: Uri? = _formUiState.value.imgUri

        if (species != null && name.trim().isNotEmpty()) {
            val plant = Plant(
                id = id,
                name = name,
                speciesName = species.name,
                imageUri = curImg?.toString(),
                species = species,
                waterHistory = emptyList(),
                created = currentDate,
                diseaseHistory = emptyList(),
                repotHistory = emptyList(),
                otherActivitiesHistory = emptyList()
            )

            viewModelScope.launch {
                val insertedPlant = plantsRepository.insert(plant)
                _formUiState.update { currentState ->
                    currentState.copy(
                        name = "",
                        species = null,
                        plantsList = currentState.plantsList.plus(insertedPlant)
                    )
                }
                resetForm()
                // Wywołaj callback po zakończeniu operacji
                onSuccess()
                populateUiState()
            }
        }

    }

    fun resetFormState(){
        viewModelScope.launch {
            _formUiState.update { currentState ->
                currentState.copy(
                    name = "",
                    species = null
                )
            }
        }
    }

    fun saveNameOnUpdate(name: String?){
        val name = name ?: _formUiState.value.name
        _formUiState.update { currentState ->
            currentState.copy(
                name = name
            )
        }
    }

    fun saveSpeciesOnUpdate(species: Species?){
        _formUiState.update { currentState ->
            currentState.copy(
                species = species
            )
        }
    }

    fun saveUriOnUpdate(uri: Uri?){
        Log.d(TAG, "We are in saveUriOnUpdate. Value of uri: ${uri}")
        _formUiState.update { currentState ->
            currentState.copy(
                imgUri = uri
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

//    fun setLastWatered(date: Calendar){
//        _formUiState.update { currentState ->
//            currentState.copy(
//                lastWatered = date
//            )
//        }
//    }

    // PODLEWANIE
    fun addWateringDate(fertilizer: String) {
        val currentlyEditedPlant = _plantUiState.value.currentlyEditedPlant

        if (currentlyEditedPlant != null) {
            val updatedPlant = currentlyEditedPlant.copy(
                waterHistory = currentlyEditedPlant.waterHistory + mapOf(fertilizer to Calendar.getInstance())
            )

            val updatedPlantsList = _formUiState.value.plantsList.map {
                if (it.id == updatedPlant.id) updatedPlant else it
            }
            viewModelScope.launch {
                plantsRepository.updateWateringHistory(currentlyEditedPlant.id, updatedPlant.waterHistory)
            }

            _formUiState.update { currentState ->
                currentState.copy(plantsList = updatedPlantsList)
            }

            _plantUiState.update { currentState ->
                currentState.copy(currentlyEditedPlant = updatedPlant)
            }
        } else {
            Log.d(TAG, "Brak edytowanej rośliny.")
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
                id = "",
//                id = currentState.id.inc(),
                name = "",
                species = null
            )
        }
    }


}
package com.example.planttrackerapp.ui


import android.net.Uri
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
import java.util.UUID
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import androidx.lifecycle.viewModelScope
import com.example.planttrackerapp.backend.repositories.SpeciesRepository
import com.example.planttrackerapp.data.SelectUiState
import com.example.planttrackerapp.data.SpeciesUiState
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FormViewModel(
    private val plantsRepository: UserPlantRepository,
    private val speciesRepository: SpeciesRepository
    ): ViewModel() {

    private val _formUiState = MutableStateFlow(FormUiState())
    val formUiState: StateFlow<FormUiState> = _formUiState.asStateFlow()

    private val _plantUiState = MutableStateFlow(PlantUiState())
    val plantUiState: StateFlow<PlantUiState> = _plantUiState.asStateFlow()

    private val _speciesUiState = MutableStateFlow(SpeciesUiState())
    val speciesUiState: StateFlow<SpeciesUiState> = _speciesUiState.asStateFlow()

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
            _speciesUiState.value = SpeciesUiState(currentlyEditedSpecies = null)
        }
    }

    fun onSetEditedSpecies(species: Species){
        _speciesUiState.update { currentState ->
            currentState.copy(
                currentlyEditedSpecies = species
            )
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

        val uri = formUri
        val species =
            if(plantSpecies!=null && formSpecies==null) plantSpecies
            else if (formSpecies!= null) formSpecies
            else Datasource.speciesList[0]

        val plantList = _formUiState.value.plantsList
        val searchedElement = plantList.filter { it.id == id}[0]
        val searchedElementId = searchedElement.id
        val resolvedImageUri = uri?.toString() ?: searchedElement.imageUri
            val searchedElementCopy = searchedElement.copy(name = name, species = species, imageUri = resolvedImageUri)
        
            viewModelScope.launch {
                Log.d("BEFORE UPDATE IMAGE URI", "${searchedElementCopy.imageUri}, ${searchedElementCopy.id}")
                plantsRepository.updateById(searchedElementCopy.id, name, species?.id, searchedElementCopy.imageUri)
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

            _plantUiState.update { currentState ->
                currentState.copy(currentlyEditedPlant = searchedElementCopy)
            }
    }

    fun onClickEditSpecies(id: String?, name: String, water: Int){
        if (id!=null){
            viewModelScope.launch {
                val speciesToInsert = speciesRepository.updateById(id, name, water)
                speciesToInsert?.let {
                    _speciesUiState.update { currentState ->
                        currentState.copy(
                            currentlyEditedSpecies = null
                        )
                    }
                }
            }
            populateUiState()
        }
    }
    fun onClickAddSpecies(name: String, water: Int){
        if (name!=null && water!=null){
            val species = Species(
                name = name,
                soilMoisture = water
            )
            viewModelScope.launch {
                val speciesToInsert = speciesRepository.insertSpecies(species)
                speciesToInsert?.let {
                    _speciesUiState.update { currentState ->
                        currentState.copy(
                            currentlyEditedSpecies = null
                        )
                    }
                }
            }
            populateUiState()
        }
    }

    fun onClickAdd(onSuccess: () -> Unit) {
        val name = _formUiState.value.name
        val species = _formUiState.value.species
        val currentDate = Calendar.getInstance()
        val curImg: Uri? = _formUiState.value.imgUri

        if (species != null && name.trim().isNotEmpty()) {
            val plant = Plant(
                name = name,
                speciesId = species.id,
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
                insertedPlant?.let {
                    _formUiState.update { currentState ->
                        currentState.copy(
                            name = "",
                            species = null,
                            plantsList = currentState.plantsList + it
                        )
                    }
                }
                resetForm()
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
                name = "",
                species = null
            )
        }
    }

}
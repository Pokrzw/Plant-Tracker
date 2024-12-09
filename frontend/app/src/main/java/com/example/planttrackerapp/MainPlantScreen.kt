package com.example.planttrackerapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.planttrackerapp.ui.PlantForm
import com.example.planttrackerapp.ui.PlantList
import com.example.planttrackerapp.ui.SinglePlantView
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme
import androidx.navigation.compose.composable
import com.example.planttrackerapp.ui.FormViewModel
import com.example.planttrackerapp.data.Datasource.plantList



enum class PlantAppScreen(){
    Form,
    PlantDetails,
    AllPlants,
    FormEdit
}
@Composable
fun PlantAppBar(modifier: Modifier = Modifier){
    Text(
        text = "HEADER"
    )
}

@Composable
fun PlantApp(
    formViewModel: FormViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val formUiState by formViewModel.formUiState.collectAsState()
    val currentPlantState by formViewModel.plantUiState.collectAsState()

    Log.d(TAG, "INIT FormUiState: ${formUiState}")
    Log.d(TAG, "INIT PlantUiState:${currentPlantState}")
    Scaffold(
        topBar = {
            PlantAppBar()
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PlantAppScreen.AllPlants.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = PlantAppScreen.AllPlants.name) {
                PlantList(
                    plantList = formUiState.plantsList,
                    onClickAddNewPlant = { navController.navigate(PlantAppScreen.Form.name) },
                    onClickDetails = { navController.navigate(PlantAppScreen.PlantDetails.name) },
                    setPlantOnClick = formViewModel::onSetPlant
                )
            }

            composable(route = PlantAppScreen.PlantDetails.name) {
                SinglePlantView(
                    plant = currentPlantState.currentlyEditedPlant,
                    onClickYes = formViewModel::onDeletePlant,
                    onGoToForm = { onGoToForm(navController) },
                    onGoBack = { onClickBack(navController) }
                )
            }

            composable(route = PlantAppScreen.Form.name) {
                PlantForm(
                    speciesList = formUiState.speciesList,
                    onClickEdit = formViewModel::onClickUpdate,
                    onClickAdd = formViewModel::onClickAdd,
                    onEditSpeciesValue = formViewModel::saveSpeciesOnUpdate,
                    onEditNameValue = formViewModel::saveNameOnUpdate,
                    onUpdateNameValue = formViewModel::saveNameOnUpdate,
                    onUpdateSpeciesValue = formViewModel::saveSpeciesOnUpdate,
                    onGoBack = { onClickBack(navController) }
                )
            }

            composable(route = PlantAppScreen.FormEdit.name) {
                PlantForm(
                    currentPlantData = currentPlantState.currentlyEditedPlant,
                    isEdit = true,
                    speciesList = formUiState.speciesList,
                    onClickEdit = formViewModel::onClickUpdate,
                    onClickAdd = formViewModel::onClickAdd,
                    onEditSpeciesValue = formViewModel::saveSpeciesOnUpdate,
                    onEditNameValue = formViewModel::saveNameOnUpdate,
                    onUpdateNameValue = formViewModel::saveNameOnUpdate,
                    onUpdateSpeciesValue = formViewModel::saveSpeciesOnUpdate,
                    onGoBack = { onClickBack(navController) }
                )
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun PlantAppPreview(modifier: Modifier = Modifier) {
    PlantTrackerAppTheme {
        PlantApp(modifier = Modifier)
    }
}

private fun onClickBack(navController: NavHostController){
    navController.navigate(PlantAppScreen.AllPlants.name)
}

private fun onGoToForm(navController: NavHostController){
    navController.navigate(PlantAppScreen.FormEdit.name)
}


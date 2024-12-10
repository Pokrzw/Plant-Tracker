package com.example.planttrackerapp

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.planttrackerapp.ui.PlantForm
import com.example.planttrackerapp.ui.PlantList
import com.example.planttrackerapp.ui.SinglePlantView
import com.example.planttrackerapp.ui.components.TopBar
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme
import com.example.planttrackerapp.ui.FormViewModel

enum class PlantAppScreen {
    Form,
    PlantDetails,
    AllPlants,
    FormEdit
}

@Composable
fun PlantApp(
    formViewModel: FormViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val formUiState by formViewModel.formUiState.collectAsState()
    val currentPlantState by formViewModel.plantUiState.collectAsState()

    // Observe currentBackStackEntry state
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Log.d(TAG, "INIT FormUiState: $formUiState")
    Log.d(TAG, "INIT PlantUiState: $currentPlantState")

    Scaffold(
        topBar = {
            val canNavigateBack = currentRoute != PlantAppScreen.AllPlants.name
            TopBar(
                title = when (currentRoute) {
                    PlantAppScreen.AllPlants.name -> "My Plants"
                    PlantAppScreen.PlantDetails.name -> "Plant Details"
                    PlantAppScreen.Form.name -> "Add A New Plant"
                    PlantAppScreen.FormEdit.name -> "Edit Plant"
                    else -> "Plant Tracker"
                },
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.popBackStack() }
            )
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
                    onGoBack = { navController.popBackStack() }
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
                    onGoBack = { navController.popBackStack() }
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
                    onGoBack = { navController.popBackStack() }
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

private fun onGoToForm(navController: NavHostController) {
    navController.navigate(PlantAppScreen.FormEdit.name)
}


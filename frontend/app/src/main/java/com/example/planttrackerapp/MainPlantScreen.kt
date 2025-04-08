package com.example.planttrackerapp

import android.util.Log

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.planttrackerapp.ui.PlantJournal
import com.example.planttrackerapp.ui.FormViewModelFactory
import com.example.planttrackerapp.backend.database.DatabaseProvider
import com.example.planttrackerapp.backend.repositories.SpeciesRepository
import com.example.planttrackerapp.backend.repositories.UserPlantRepository
import com.example.planttrackerapp.ui.ActivityJournal
import com.example.planttrackerapp.ui.QRCodeScanner
import com.example.planttrackerapp.ui.QRScannerScreen


enum class PlantAppScreen {
    Form,
    ActivityJournal,
    PlantDetails,
    AllPlants,
    FormEdit,
    PlantJournal,
    QRScanner
}

// Funkcja sprawdzająca czy w BackStack nie ma już aktualnego route'a
fun NavHostController.navigateIfNotCurrent(route: String) {
    if (this.currentBackStackEntry?.destination?.route != route) {
        this.navigate(route)
    }
}

@Composable
fun PlantApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    //================================================
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val plantRepository = UserPlantRepository(database.userPlantDao(), database.speciesDao())
    val speciesRepository = SpeciesRepository(database.speciesDao())
    val formViewModel: FormViewModel = viewModel(
        factory = FormViewModelFactory(plantRepository, speciesRepository)
    )
    //===================================================

    val formUiState by formViewModel.formUiState.collectAsState()
    val currentPlantState by formViewModel.plantUiState.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            val canNavigateBack = currentRoute != PlantAppScreen.AllPlants.name
            TopBar(
                title = when (currentRoute) {
                    PlantAppScreen.AllPlants.name -> "My Plants"
                    PlantAppScreen.PlantDetails.name -> "Plant Details"
                    PlantAppScreen.Form.name -> "Add A New Plant"
                    PlantAppScreen.FormEdit.name -> "Edit Plant"
                    PlantAppScreen.PlantJournal.name -> "Plant Journal"
                    PlantAppScreen.ActivityJournal.name -> "${currentPlantState.currentlyEditedPlant?.name}'s journal"
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
                    onClickAddNewPlant = { navController.navigateIfNotCurrent(PlantAppScreen.Form.name) },
                    onClickDetails = { navController.navigateIfNotCurrent(PlantAppScreen.PlantDetails.name) },
                    setPlantOnClick = formViewModel::onSetPlant,
                    onClickOpenQRScanner = { navController.navigateIfNotCurrent(PlantAppScreen.QRScanner.name) }
                )
            }

            composable(route = PlantAppScreen.ActivityJournal.name){
                ActivityJournal(
                    currentPlant = currentPlantState.currentlyEditedPlant,
                    onGoBack = { navController.popBackStack() }
                )
            }

            composable(route = PlantAppScreen.PlantDetails.name) {
                SinglePlantView(
                    plant = currentPlantState.currentlyEditedPlant,
                    onClickYes = formViewModel::onDeletePlant,
                    onWater = formViewModel::addWateringDate,
                    onGoToActivityJournal = { onGoToToActivityJournal(navController) },
                    onGoToForm = { onGoToForm(navController) },
                    onGoBack = { navController.popBackStack() }
                )
            }

            composable(route = PlantAppScreen.Form.name) {
                PlantForm(
                    speciesList = formUiState.speciesList,
                    onClickEdit = formViewModel::onClickUpdate,
                    onClickAdd = {
                        formViewModel.onClickAdd {
                            Log.d("nav", "back")
                            navController.popBackStack()
                        }
                    },
                    formViewModel = formViewModel,
                    onUploadImage = formViewModel::saveUriOnUpdate,
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
                    onClickAdd =  {
                        formViewModel.onClickAdd {
                            navController.popBackStack()
                        }
                    },

                    onEditSpeciesValue = formViewModel::saveSpeciesOnUpdate,
                    onEditNameValue = formViewModel::saveNameOnUpdate,
                    onUploadImage = formViewModel::saveUriOnUpdate,
                    onUpdateNameValue = formViewModel::saveNameOnUpdate,
                    onUpdateSpeciesValue = formViewModel::saveSpeciesOnUpdate,
                    onGoBack = { navController.popBackStack() }
                )
            }

            composable(route = PlantAppScreen.QRScanner.name) {
                QRScannerScreen(
                    setPlantOnScan = formViewModel::onSetPlant,
                    onWater = formViewModel::addWateringDate,
                    onClickDetails = { navController.navigateIfNotCurrent(PlantAppScreen.PlantDetails.name) },
                    formViewModel = formViewModel // Pass the ViewModel for plant lookup
                )
            }


            composable(route = PlantAppScreen.PlantJournal.name) {
                PlantJournal(
                    plant = currentPlantState.currentlyEditedPlant
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
    navController.navigateIfNotCurrent(PlantAppScreen.FormEdit.name)
}

private fun onGoToToActivityJournal(navController: NavHostController){
    navController.navigateIfNotCurrent(PlantAppScreen.ActivityJournal.name)
}
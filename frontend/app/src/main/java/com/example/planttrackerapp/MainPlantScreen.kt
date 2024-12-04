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
    AllPlants
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
                    onClickB = { navController.navigate(PlantAppScreen.Form.name) },
                    onClick = { navController.navigate(PlantAppScreen.PlantDetails.name) }
                )
            }

            composable(route = PlantAppScreen.PlantDetails.name) {
                SinglePlantView(
                    // Placeholder Plant, zanim zostanie dodana poprawna nawigacja
                    plant = plantList[1],
                    onGoBack = { onClickBack(navController) }
                )
            }

            composable(route = PlantAppScreen.Form.name) {
                PlantForm(
                    speciesList = formUiState.speciesList,
                    onClickAdd = formViewModel::onClickAdd,
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

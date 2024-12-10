package com.example.planttrackerapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.SinglePlantCard


@Composable
fun PlantList(
    plantList: List<Plant>,
    onClickAddNewPlant: () -> Unit = {},
    onClickDetails: (Plant) -> Unit,
    setPlantOnClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {

    var searchedName by remember { mutableStateOf("") }
    var displayedPlants by remember { mutableStateOf(plantList) }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchedName,
            onValueChange = {
                searchedName = it
                displayedPlants = filterNames(it, plantList)
                            },
            label = { Text("Search by name") }
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(displayedPlants.size) { index ->
                SinglePlantCard(
                    plant = displayedPlants[index],
                    onItemClick = onClickDetails,
                    onSetPlant = setPlantOnClick
                )
            }
        }
        Button(
            onClick = { onClickAddNewPlant() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Add new plant"
            )
        }
    }
}

fun filterNames(namePart: String, plantList: List<Plant>): List<Plant>{
    val ignoreCasePart = namePart.toLowerCase()
    val newList = plantList.filter { it.name.toLowerCase().contains(ignoreCasePart) }
    return newList
}
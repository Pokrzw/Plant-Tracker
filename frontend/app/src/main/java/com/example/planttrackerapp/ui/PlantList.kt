package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.SinglePlantCard
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.QrCodeScanner


@Composable
fun PlantList(
    plantList: List<Plant>,
    onClickAddNewPlant: () -> Unit = {},
    onClickOpenQRScanner: () -> Unit = {},
    onClickSelectPlants: () -> Unit = {},

    onClickDetails: (Plant) -> Unit,
    setPlantOnClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {

    var searchedName by remember { mutableStateOf("") }

    val displayedPlants = remember(searchedName, plantList) {
        if (searchedName.isEmpty()) plantList
        else plantList.filter { it.name.contains(searchedName, ignoreCase = true) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchedName,
                    onValueChange = { searchedName = it },
                    label = { Text("Search by name") },
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) }
                )
            }
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
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { onClickOpenQRScanner() },
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan QR Code")
                }
                FloatingActionButton(
                    onClick = { onClickAddNewPlant() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new plant"
                    )
                }
                FloatingActionButton(
                    onClick = { onClickSelectPlants() },
                ) {
                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = "Select plants"
                    )
                }
            }
        }
    }
}

fun filterNames(namePart: String, plantList: List<Plant>): List<Plant>{
    val ignoreCasePart = namePart.lowercase()
    val newList = plantList.filter { it.name.lowercase().contains(ignoreCasePart) }
    return newList
}
package com.example.planttrackerapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.SinglePlantCardSelectable

@SuppressLint("UnrememberedMutableState")
@Composable
fun ChoosePlantsToSelect(
    plantList: List<Plant>,
    onSelectPlants: (List<Plant>) -> Unit,
    onClickSelect: () -> Unit
){
    var selectedPlantList: ArrayList<Plant> = arrayListOf()
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
                        SinglePlantCardSelectable(
                            plant = displayedPlants[index],
                            onCheckPlant =  { b: Boolean, plant: Plant ->
                                if (b){
                                    selectedPlantList.add(plant)
                                } else {
                                    if (selectedPlantList.contains(plant)){
                                        selectedPlantList.remove(plant)
                                    }
                                }
                            }
                        )
                    }
                }
                Button(
                    onClick = {
                        onSelectPlants(selectedPlantList)
                        onClickSelect()
                    }
                ) {
                    Text("Select")
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

                }
            }


        }
    }


@Preview(showBackground = true)
@Composable
fun ChoosePlantToSelectPreview(modifier: Modifier = Modifier){
    ChoosePlantsToSelect(plantList = listOf(Datasource.plantList[0], Datasource.plantList[1]), onSelectPlants = {}, onClickSelect = {})
}


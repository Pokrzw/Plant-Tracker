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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.model.Species
import com.example.planttrackerapp.ui.components.SingleSpeciesCard


@Composable
fun SpeciesList(
    speciesList: List<Species>,
    onClickAddNewSpecies: () -> Unit = {},
    setSpeciesOnClick: (Species) -> Unit,
    modifier: Modifier = Modifier
) {

    var searchedName by remember { mutableStateOf("") }

    val displayedSpecies = remember(searchedName, speciesList) {
        if (searchedName.isEmpty()) speciesList
        else speciesList.filter { it.name.contains(searchedName, ignoreCase = true) }
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
                items(displayedSpecies.size) { index ->
                    SingleSpeciesCard(
                        species = displayedSpecies[index],
                        onSetSpecies = setSpeciesOnClick
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
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { onClickAddNewSpecies() },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new species"
                    )
                }
            }
        }
    }
        }

fun filterNames(namePart: String, speciesList: List<Species>): List<Species>{
    val ignoreCasePart = namePart.lowercase()
    val newList = speciesList.filter { it.name.lowercase().contains(ignoreCasePart) }
    return newList
}
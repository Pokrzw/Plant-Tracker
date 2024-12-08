package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(plantList.size) { index ->
                SinglePlantCard(
                    plant = plantList[index],
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

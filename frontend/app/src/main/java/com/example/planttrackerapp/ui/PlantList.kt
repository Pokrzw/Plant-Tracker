package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.SinglePlantCard

@Composable
fun PlantList(
    onClickB: () -> Unit = {},
    onClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    val plants = Datasource.plantList
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(plants.size) { index ->
                SinglePlantCard(
                    plant = plants[index],
                    onItemClick = onClick
                )
            }
        }
        Button(
            onClick = { onClickB() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Add new plant"
            )
        }
    }
}

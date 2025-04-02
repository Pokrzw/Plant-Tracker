package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.PlantQRComponent

@Composable
fun PlantQRList(
    plantList: List<Plant>
){
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        LazyColumn  {
            items(plantList.size) { index ->
                PlantQRComponent(
                    plant = plantList[index]
                )
            }
        }

        Button(
            onClick = {}
        ) {
            Text("Export")
        }
    }
}

@Preview(showBackground=true)
@Composable
fun PreviewPlantQRList(){
    PlantQRList(
        plantList = listOf(Datasource.plantList[0],Datasource.plantList[1])
    )
}
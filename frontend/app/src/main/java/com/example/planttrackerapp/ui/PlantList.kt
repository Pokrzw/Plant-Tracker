package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.SinglePlantCard

@Composable
fun PlantList(
    // onClickA: () -> Unit = {},
    onClickB: () -> Unit = {},
    onClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
){
//    Column {
//        Text(
//            text = "PlantApp"
//        )
//        Button(
//            onClick = {
//                onClickA()
//            }
//        ) {
//            Text(
//                text = "A"
//            )
//        }
//        Button(
//            onClick = { onClickB() }
//        ) {
//            Text(
//                text = "B"
//            )
//        }
//    }
// Trzeba dodać do roślin onClick żeby dało się przechodzić do ich detali (SinglePlantView)
    val plants = Datasource.plantList
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(plants.size) { index ->
            SinglePlantCard(
                plant = plants[index],
                onItemClick = onClick)
        }
    }
}
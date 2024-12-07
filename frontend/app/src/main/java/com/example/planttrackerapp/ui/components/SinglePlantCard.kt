package com.example.planttrackerapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant

@Composable
fun SinglePlantCard(
    plant: Plant,
    onItemClick: (Plant) -> Unit,
    onSetPlant: (Plant) -> Unit
) {
//    Log.d(TAG, "SinglePlantCard: ${plant}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            Log.d(TAG,"I'M CLICKING!!!")
            onSetPlant(plant)
            onItemClick(plant)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = plant.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Species: ${plant.species.name}",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground=true)
@Composable
fun PreviewSingleCard(){
    SinglePlantCard(
        plant = Datasource.plantList[0],
        onItemClick = {},
        onSetPlant = {}
    )
}
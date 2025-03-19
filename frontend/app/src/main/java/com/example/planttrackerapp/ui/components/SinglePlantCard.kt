package com.example.planttrackerapp.ui.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.planttrackerapp.R
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
    val image = painterResource(R.drawable.imgsmall)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            Log.d(TAG,"SinglePlantCard.kt: I'M CLICKING!!!")
            onSetPlant(plant)
            onItemClick(plant)
        }
    ) {
        Log.d(TAG, "plant.imageUri: ${plant.imageUri}")
        Row(modifier = Modifier.padding(16.dp)){
            if(plant.imageUri != null){
                Log.d(TAG, "name: ${plant.name}|IF (nie jest null) plant.imageUri: ${plant.imageUri}")

                AsyncImage(
                    model = Uri.parse(plant.imageUri),
                    contentDescription = plant.imageUri,
                    modifier = Modifier.fillMaxWidth(0.1f)
                )
            } else {
                Log.d(TAG, "name: ${plant.name}|ELSE (jest null) plant.imageUri: ${plant.imageUri}")
                Image(
                    painter = image,
                    contentDescription = null
                )

            }

            Column(modifier = Modifier.padding(start = 4.dp)) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Species: ${plant.species?.name ?: "unknown"}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
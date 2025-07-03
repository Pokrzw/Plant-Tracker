package com.example.planttrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.R
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant


@Composable
fun SinglePlantCardSelectable(
    plant: Plant,
    onCheckPlant: (Boolean, Plant) -> Unit = { b: Boolean, plant: Plant -> {}}
) {

    var checked by remember { mutableStateOf(false) }
    val image = painterResource(R.drawable.imgsmall)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(0.8f)){
                Image(
                    painter = image,
                    contentDescription = null

                )
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
            Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        onCheckPlant(checked, plant)
                    }
                )
        }


    }
}

@Preview(showBackground=true)
@Composable
fun PreviewSingleCardSelectable(){
    SinglePlantCardSelectable(
        plant = Datasource.plantList[0],
    )
}
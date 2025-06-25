package com.example.planttrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planttrackerapp.backend.database.base64ToBitmap
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant

@Composable
fun PlantQRComponent(
    plant: Plant
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 150.dp, height = 200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = plant.name,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = plant.species?.name ?: "",
                    fontSize = 8.sp
                )
            }

            plant.qrCodeImage?.let { qrCodeBase64 ->
                val qrBitmap = remember { base64ToBitmap(qrCodeBase64) }

                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "QR Code for ${plant.name}",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPlantQRComponent(){
    PlantQRComponent(plant = Datasource.plantList[0])
}
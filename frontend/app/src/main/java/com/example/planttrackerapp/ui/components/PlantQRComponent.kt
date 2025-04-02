package com.example.planttrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.backend.database.base64ToBitmap
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant

@Composable
fun PlantQRComponent(
    plant: Plant
){
    Column(
        modifier = Modifier.padding(16.dp)
    ){
        Text(
            text=plant.name
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text=plant.species?.name ?: ""
        )
        plant?.qrCodeImage?.let { qrCodeBase64 ->
            val qrBitmap = remember { base64ToBitmap(qrCodeBase64) }

            Image(
                bitmap = qrBitmap.asImageBitmap(),
                contentDescription = "QR Code for ${plant.name}",
                modifier = Modifier
                    .size(200.dp)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantQRComponent(){
    PlantQRComponent(plant = Datasource.plantList[0])
}
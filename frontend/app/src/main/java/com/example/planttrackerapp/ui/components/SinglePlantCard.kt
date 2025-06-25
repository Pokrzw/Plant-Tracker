package com.example.planttrackerapp.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.planttrackerapp.R
import com.example.planttrackerapp.data.Datasource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import com.example.planttrackerapp.model.Plant



@Composable
fun SinglePlantCard(
    plant: Plant,
    onItemClick: (Plant) -> Unit,
    onSetPlant: (Plant) -> Unit
) {
    val image = painterResource(R.drawable.imgsmall)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            onSetPlant(plant)
            onItemClick(plant)
        }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(width = 60.dp, height = 80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
            ) {
                if (plant.imageUri != null) {
                    AsyncImage(
                        model = Uri.parse(plant.imageUri),
                        contentDescription = plant.imageUri,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                    )
                } else {
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .matchParentSize()
                    )
                }

            }

            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
            ) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = plant.species?.name ?: "unknown",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic
                    ),
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
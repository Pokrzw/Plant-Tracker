package com.example.planttrackerapp.ui.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.planttrackerapp.R
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
@Composable
fun SinglePlantCardSelectable(
    plant: Plant,
    onCheckPlant: (Boolean, Plant) -> Unit = { _, _ -> }
) {
    var checked by remember { mutableStateOf(false) }
    val image = painterResource(R.drawable.imgsmall)

    val borderModifier = if (checked) {
        Modifier.border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp)
        )
    } else {
        Modifier // no border applied
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .then(borderModifier)
            .clickable {
                checked = !checked
                onCheckPlant(checked, plant)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }
            }

            Column(
                modifier = Modifier
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
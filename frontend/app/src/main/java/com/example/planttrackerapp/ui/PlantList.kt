package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlantList(
    onClickA: () -> Unit = {},
    onClickB: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column {
        Text(
            text = "PlantApp"
        )
        Button(
            onClick = {
                onClickA()
            }
        ) {
            Text(
                text = "A"
            )
        }
        Button(
            onClick = { onClickB() }
        ) {
            Text(
                text = "B"
            )
        }
    }

}
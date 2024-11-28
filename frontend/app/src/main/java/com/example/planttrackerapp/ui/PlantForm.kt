package com.example.planttrackerapp.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlantForm(
    onGoBack: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Text(
        text = "Form"
    )
    Button(
        onClick = {
            onGoBack()
        }
    ) {
        Text(
            text = "<-"
        )
    }
}
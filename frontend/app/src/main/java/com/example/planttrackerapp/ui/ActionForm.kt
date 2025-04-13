package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant

@Composable
fun ActionForm(
    plant: Plant,
    isRepot: Boolean,
    isDisease: Boolean,
    isOther: Boolean,
    onRepot: (Plant) -> Unit,
    onDisease: (Plant) -> Unit,
    onOther: (Plant) -> Unit
){
    Column {
        if (isRepot){
            Text("Document new repot (soil type, pot size...)")
        } else if (isDisease) {
            Text("Document disease")
        } else {
            Text("Document a treatment")
        }
        Text(plant.name)
    }



}

@Preview(showBackground = true)
@Composable
fun ActionFormPrev(modifier: Modifier = Modifier){
    ActionForm(
        plant = Datasource.plantList.get(0),
        isRepot = false,
        isDisease = false,
        isOther = true,
        onRepot = {},
        onDisease = {},
        onOther = {}
    )
}
package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.ui.components.DropDownWrapper

@Composable
fun PlantForm(
    onGoBack: () -> Unit = {},
    isEdit: Boolean = false,
    modifier: Modifier = Modifier
){
   Column {
       if (isEdit){
           Text(
               text = "Edit specimen"
           )
       } else{
           Text(
               text = "Add specimen"
           )
       }

       FormBody(modifier)
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
}

@Composable
fun FormBody(modifier: Modifier){
    var plantName by remember { mutableStateOf("") }
    Column {
        TextField(
            value = plantName,
            onValueChange = {plantName = it},
            label = {Text("Name of plant")}
        )

        DropDownWrapper(
            items = Datasource.speciesList,
            label = "Species"
        )

    }
}

@Preview(showBackground = true)
@Composable
fun FormPreview(modifier: Modifier = Modifier) {
    PlantForm()
}

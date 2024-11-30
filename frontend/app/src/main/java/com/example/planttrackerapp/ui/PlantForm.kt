package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.ui.components.DropDownWrapper

@Composable
fun PlantForm(
    onGoBack: () -> Unit = {},
    onUpdateValue: () -> Unit = {},
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

       FormBody(
           onUpdateValue = { },
           modifier = modifier
       )

       Row{
           Button(
               onClick = {
                   onGoBack()
               }
           ) {
               Text(
                   text = "<-"
               )
           }

           Button(
               onClick = {
                   onGoBack()
               }
           ) {
               Text(
                   text = "Add"
               )
           }
       }
   }
}

@Composable
fun FormBody(
    onUpdateValue: () -> Unit = {},
    modifier: Modifier = Modifier
){
    var plantName by remember { mutableStateOf("") }
    Column {
        TextField(
            value = plantName,
            onValueChange = {plantName = it},
            label = {Text("Name of plant")}
        )

        DropDownWrapper(
            items = Datasource.speciesList,
            onUpdateValue = onUpdateValue,
            label = "Species"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormPreview(modifier: Modifier = Modifier) {
    PlantForm()
}

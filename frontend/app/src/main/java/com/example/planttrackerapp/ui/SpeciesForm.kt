package com.example.planttrackerapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.model.Species

@Composable
fun SpeciesForm(
    onGoBack: () -> Unit,
    onAdd: (String, Int)-> Unit,
    onEdit: () -> Unit = {},
    species: Species? = null,
    isEdit: Boolean
){
    val label = if (isEdit){
        "Edit"
    } else {
        "Add"
    }
    var test by remember { mutableStateOf(false) }
    var text by remember{mutableStateOf(species?.name ?: "")}
    var soilMoist by remember { mutableStateOf(species?.soilMoisture.toString() ?: "") }

    Column(modifier = Modifier.
    fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text("${label} species")
        TextField(
            value = text,
            onValueChange = {text = it},
            label = {Text("Change name")},
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = soilMoist.toString(),
            onValueChange = {
                soilMoist = it
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            label = {Text("Change soil moisture")},
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                val soilFinal =
                    if(soilMoist.toInt()<0){1}
                    else if(soilMoist.toInt()>10){10}
                    else{soilMoist.toInt()}
                onAdd(text, soilFinal)
                onGoBack()
            }
        ) {
            Text(label)
        }
    }


}
package com.example.planttrackerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.reflect.KFunction2

@Composable
fun ActionForm(
    isRepot: Boolean,
    isDisease: Boolean,
    onSubmit: KFunction2<String, String, Unit>,
    onGoBack: () -> Unit
){
    var text by remember { mutableStateOf("") }
    val option = if (isRepot) {"new repot (soil type, pot size...)"}
    else if(isDisease){"disease"}
    else{"other"}

    Column {
       Text("Document a new ${option}")
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = {Text(option)},
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                val funOption = if (isRepot) "repot"
                else if (isDisease) "disease"
                else "other"
                onSubmit( text, funOption)
                onGoBack()
            }
        ) {
            Text("Add")
        }
    }
}


package com.example.planttrackerapp.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Species
import com.example.planttrackerapp.ui.theme.PlantTrackerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownWrapper(
    items: List<Species>,
    label: String,
    onUpdateValue: (Species?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSpecies by remember { mutableStateOf(items[0]) }
    val textFieldState = rememberTextFieldState("Select species")

    Column(
        modifier = Modifier
            .fillMaxWidth() ,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {isExpanded = !isExpanded}
        ) {

           TextField(
               modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
               state = textFieldState,
               readOnly = true,
               label = { Text(label) },
               trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)},
               colors = TextFieldDefaults.colors(
                   unfocusedIndicatorColor = Color.Transparent,
                   focusedIndicatorColor = Color.Transparent
               ),
               shape = RoundedCornerShape(16.dp),
           )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = !isExpanded}
            ) {
                items.forEach{ option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            textFieldState.setTextAndPlaceCursorAtEnd(option.name)
                            isExpanded = false
                            selectedSpecies = option
                            Log.d(TAG, "Current species: ${option.toString()}")
                            onUpdateValue(selectedSpecies)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownPreview(modifier: Modifier = Modifier) {
    PlantTrackerAppTheme {
        DropDownWrapper(
            items = Datasource.speciesList,
            label = "Species",
            modifier = Modifier
        )
    }
}

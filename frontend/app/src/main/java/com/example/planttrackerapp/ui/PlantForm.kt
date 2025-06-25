package com.example.planttrackerapp.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planttrackerapp.TAG
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.model.Species
import com.example.planttrackerapp.ui.components.DropDownWrapper
import androidx.lifecycle.viewModelScope
import com.example.planttrackerapp.ui.FormViewModel
import com.example.planttrackerapp.ui.components.ImageField

@Composable
fun PlantForm(
    resetForm: () -> Unit,
    formViewModel: FormViewModel? = null,
    onClickEdit: () -> Unit,
    currentPlantData: Plant? = null,
    onClickAdd: () -> Unit = {},
    speciesList: List<Species> = emptyList(),
    onGoBack: () -> Unit = {},
    onEditNameValue: (String?) -> Unit,
    onUploadImage: (Uri?) -> Unit,
    onEditSpeciesValue: (Species?) -> Unit,
    onUpdateNameValue: (String?) -> Unit = {},
    onUpdateSpeciesValue: (Species?) -> Unit = {},
    isEdit: Boolean = false,
    modifier: Modifier = Modifier
) {
    resetForm()
    Log.d(TAG, "Current Plant Data: ${currentPlantData}")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 4.dp,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isEdit) "Edit specimen" else "Add specimen",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    FormBody(
                        isEdit = isEdit,
                        currentPlantData = currentPlantData,
                        speciesList = speciesList,
                        onEditSpeciesValue = onEditSpeciesValue,
                        onUploadImage = onUploadImage,
                        onEditNameValue = onEditNameValue,
                        onUpdateNameValue = onUpdateNameValue,
                        onUpdateSpeciesValue = onUpdateSpeciesValue,
                        modifier = modifier
                    )

                    Button(
                        modifier = Modifier
                            .padding(top = 28.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (isEdit) {
                                onClickEdit()
                                onGoBack()
                            } else {
                                Log.d("add", "formViewModel: $formViewModel")
                                formViewModel?.onClickAdd {
                                    Log.d("add", "onadd")
                                    onGoBack()
                                }
                            }
                        }
                    ) {
                        Text(text = if (isEdit) "Edit" else "Add")
                    }
                }
            }
        }
    }
}


@Composable
fun FormBody(
    isEdit: Boolean,
    currentPlantData: Plant? = null,
    speciesList: List<Species>,
    onEditNameValue: (String?) -> Unit,
    onEditSpeciesValue: (Species?) -> Unit,
    onUploadImage: (Uri?) -> Unit,
    onUpdateNameValue: (String?) -> Unit = {},
    onUpdateSpeciesValue: (Species?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val name = currentPlantData?.name ?: ""
    var plantName by remember { mutableStateOf(name) }
    val speciesCallback = if (isEdit) onEditSpeciesValue else onUpdateSpeciesValue

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageField(
            onUploadImage = onUploadImage,
            plant = currentPlantData
        )

        TextField(
            value = plantName,
            onValueChange = {
                plantName = it
                if (isEdit) onEditNameValue(it)
                else onUpdateNameValue(it)
            },
            label = { Text("Name of plant") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(bottom = 16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        DropDownWrapper(
            items = speciesList,
            onUpdateValue = speciesCallback,
            label = "Species",
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FormPreview(modifier: Modifier = Modifier) {
    PlantForm(onEditSpeciesValue = {}, onEditNameValue = {}, onClickEdit = {}, resetForm = {}, onUploadImage = {})
}



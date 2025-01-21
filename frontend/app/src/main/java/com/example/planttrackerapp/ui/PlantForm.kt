package com.example.planttrackerapp.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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

@Composable
fun PlantForm(
    formViewModel: FormViewModel? = null,
    onClickEdit: () -> Unit,
    currentPlantData: Plant? = null,
    onClickAdd: () -> Unit = {},
    speciesList: List<Species> = emptyList(),
    onGoBack: () -> Unit = {},
    onEditNameValue: (String?) -> Unit,
    onEditSpeciesValue: (Species?) -> Unit,
    onUpdateNameValue: (String?) -> Unit = {},
    onUpdateSpeciesValue: (Species?) -> Unit = {},
    isEdit: Boolean = false,
    modifier: Modifier = Modifier
){
    Log.d(TAG, "Current Plant Data: ${currentPlantData}")
   Column (
       modifier = Modifier.fillMaxWidth(),
       horizontalAlignment = Alignment.CenterHorizontally
   ){
       if (isEdit){
           Text(
               modifier = Modifier.padding(16.dp),
               fontWeight = FontWeight.Bold,
               fontSize = 24.sp,
               text = "Edit specimen"
           )
       } else{
           Text(
               modifier = Modifier.padding(16.dp),
               fontWeight = FontWeight.Bold,
               fontSize = 24.sp,
               text = "Add specimen"
           )
       }

       FormBody(
           isEdit = isEdit,
           currentPlantData = currentPlantData,
           speciesList = speciesList,
           onEditSpeciesValue = onEditSpeciesValue,
           onEditNameValue = onEditNameValue,
           onUpdateNameValue = onUpdateNameValue,
           onUpdateSpeciesValue = onUpdateSpeciesValue,
           modifier = modifier
       )

       Row{
           if(isEdit){
               Button(
                   modifier = Modifier.padding(top = 16.dp),
                   onClick = {
                       onClickEdit()
                       onGoBack()
                   }
               ) {
                   Text(
                       text = "Edit"
                   )
               }
           } else {
               Button(
                   modifier = Modifier.padding(top = 16.dp),
                   onClick = {
                       Log.d("add", "formViewModel: $formViewModel")
                       formViewModel?.onClickAdd {
                           Log.d("add", "onadd")
                           onGoBack()
                       }
                   }
               ) {
                   Text(
                       text = "Add"
                   )
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
    onUpdateNameValue: (String?) -> Unit = {},
    onUpdateSpeciesValue: (Species?) -> Unit = {},
    modifier: Modifier = Modifier
){
    val name = currentPlantData?.name ?: ""
    var plantName by remember { mutableStateOf(name) }
    val funToBePassed =
        if (isEdit) onEditSpeciesValue
        else onUpdateSpeciesValue

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        TextField(
            value = plantName,
            onValueChange = {
                plantName = it
                if (isEdit){
                    onEditNameValue(it)
                } else {
                    onUpdateNameValue(it)
                }
                            },
            label = {Text("Name of plant")},
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DropDownWrapper(
            items = speciesList,
            onUpdateValue = funToBePassed,
            label = "Species"
        )
    }
}



@Preview(showBackground = true)
@Composable
fun FormPreview(modifier: Modifier = Modifier) {
    PlantForm(onEditSpeciesValue = {}, onEditNameValue = {}, onClickEdit = {})
}



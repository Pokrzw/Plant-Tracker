package com.example.planttrackerapp.ui.components

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.TAG

@SuppressLint("SuspiciousIndentation")
@Composable
fun ImageField(
    onUploadImage: (Uri?) -> Unit,
    plant: Plant? = null,
    modifier: Modifier = Modifier
){

    var startingVal: Uri? = null
    if(plant?.imageUri != null){
        startingVal = Uri.parse(plant.imageUri)
    }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(startingVal)
    }

    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            Log.d(TAG, "URI: ${uri}")
            onUploadImage(uri)
        }
    )
        AsyncImage(
            model = selectedImageUri,
            contentDescription = "",
            modifier = Modifier
                .width(228.dp)
                .height(324.dp)
        )


    Row(modifier = Modifier){
        Button(onClick = {
            singleImagePickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Text("Choose image")
        }

        Button(onClick = {
            selectedImageUri = null
            onUploadImage(selectedImageUri)
        }) {
            Text("Clear image")
        }
    }



}

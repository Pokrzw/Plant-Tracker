package com.example.planttrackerapp.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.TAG
import java.io.File
import java.io.FileOutputStream


fun copyImageToInternalStorage(context: Context, sourceUri: Uri, filename: String): String? {

    return try {
        val inputStream = context.contentResolver.openInputStream(sourceUri)
        val file = File(context.filesDir, filename)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ImageField(
    onUploadImage: (Uri?) -> Unit,
    plant: Plant? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var startingVal: Uri? = null

    if (plant?.imageUri != null) {
        startingVal = Uri.parse(plant.imageUri)
    }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(startingVal)
    }

    val singleImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                Log.d(TAG, "URI: ${uri}")

                val pathToImage = copyImageToInternalStorage(
                    context,
                    uri,
                    "plant_image_${System.currentTimeMillis()}.jpg")

                if (pathToImage != null) {
                    Log.d(TAG, "Obrazek skopiowany do ścieżki: ${pathToImage}")
                    onUploadImage(Uri.fromFile(File(pathToImage)))
                }
                selectedImageUri = Uri.fromFile(File(pathToImage))
            } else {
                Log.e(TAG, "Nie udało się skopiować obrazka")
            }
        }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedImageUri?.let { uri ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = if (uri.scheme == "file") File(uri.path) else uri,
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .width(220.dp)
                        .aspectRatio(10f / 14f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    singleImagePickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Choose image")
            }

            OutlinedButton(
                onClick = {
                    selectedImageUri = Uri.parse("Clear image")
                    onUploadImage(selectedImageUri)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear image")
            }
        }
    }
}

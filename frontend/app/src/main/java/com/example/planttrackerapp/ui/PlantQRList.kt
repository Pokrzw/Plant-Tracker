package com.example.planttrackerapp.ui

import android.content.ContentValues
import android.graphics.Paint
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.example.planttrackerapp.backend.database.base64ToBitmap
import com.example.planttrackerapp.data.Datasource
import com.example.planttrackerapp.model.Plant
import com.example.planttrackerapp.ui.components.PlantQRComponent
import kotlinx.coroutines.launch

@Composable
fun PlantQRList(
    context: Context,
    plantList: List<Plant>
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Button(
                    onClick = {
                        coroutineScope.launch {
                            exportToPDF(context, plantList)
                            focusManager.clearFocus()
                        }

                    }
                ) {
                    Text("Export")
                }
                LazyColumn  {
                    items(plantList.size) { index ->
                        PlantQRComponent(
                            plant = plantList[index]
                        )
                    }
                }

            }
}

fun exportToPDF(
    context: Context,
    plantList: List<Plant>){
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas
    val paint = Paint()

    var curHeight = 20f
    var curWidth = 1f
    for (plant in plantList) {
        canvas.drawText(plant.name, curWidth, curHeight, paint)
        curWidth+= 200f
        if (curWidth>=600f){
            curWidth = 1f
            curHeight+=200f
        }

//        plant?.qrCodeImage?.let { qrCodeBase64 ->
//            val qrBitmap = base64ToBitmap(qrCodeBase64)
//            val scaledBitmap = qrBitmap.let {Bitmap.createScaledBitmap(qrBitmap, 200, 200, false) }
//            canvas.drawBitmap(scaledBitmap, (canvas.width/2- 75).toFloat(), 250f, paint)
//        }
    }
//    paint.textSize = 20f
//    paint.textAlign = Paint.Align.CENTER
//    paint.isFakeBoldText = false

    pdfDocument.finishPage(page)

    val pdfFileName = "Plant_QR_Codes_${System.currentTimeMillis()}.pdf"
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME,pdfFileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
    }
    val pdfUri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

    if (pdfUri != null){
        resolver.openOutputStream(pdfUri).use {
            pdfDocument.writeTo(it)
            Toast.makeText(context, "PDF saved as ${pdfFileName}", Toast.LENGTH_LONG).show()

        }
    }

    pdfDocument.close()
}
@Preview(showBackground=true)
@Composable
fun PreviewPlantQRList(){
    PlantQRList(
        plantList = listOf(Datasource.plantList[0],Datasource.plantList[1]),
        context = LocalContext.current
    )
}
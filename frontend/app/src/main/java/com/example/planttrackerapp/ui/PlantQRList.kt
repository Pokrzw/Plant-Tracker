package com.example.planttrackerapp.ui

import android.content.ContentValues
import android.graphics.Paint
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    plantList: List<Plant>,
    onGoBack: () -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                exportToPDF(context, plantList)
                                focusManager.clearFocus()
                                onGoBack()
                            }
                        }
                    ) {
                        Text("Export")
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(120.dp)
                )  {
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
    plantList: List<Plant>
){
    val QR_CODE_SIZE = 150
    val PAGE_WIDTH = 595
    val PAGE_HEIGHT = 842
    var pageNumber = 1
    val pdfDocument = PdfDocument()
    val pageInfo = {
            n : Int -> PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, n).create()
    }
    val page = pdfDocument.startPage(pageInfo(pageNumber))
    var curPage = page
    val paint = Paint()

    var curHeight = 20f
    var curWidth = 1f
    for (plant in plantList) {

        curPage.canvas.drawText(plant.name, curWidth+15f, curHeight, paint)
        curPage.canvas.drawText(plant.speciesName, curWidth+15f, curHeight+15f, paint)
        plant?.qrCodeImage?.let { qrCodeBase64 ->
            val qrBitmap = base64ToBitmap(qrCodeBase64)
            val scaledBitmap = qrBitmap.let {Bitmap.createScaledBitmap(qrBitmap, QR_CODE_SIZE, QR_CODE_SIZE, false) }
            curPage.canvas.drawBitmap(scaledBitmap, curWidth, curHeight + 20f, paint)
        }

        curWidth+= 150f
        if (curWidth>=460f){
            curWidth = 1f
            curHeight+=180f
        }


        if (curHeight >= 600){
            pdfDocument.finishPage(curPage)
            pageNumber = pageNumber + 1
            curPage = pdfDocument.startPage(pageInfo(pageNumber))
            curHeight = 20f
            curWidth = 1f
        }
}
    pdfDocument.finishPage(curPage)

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
        onGoBack = {}
    )
}
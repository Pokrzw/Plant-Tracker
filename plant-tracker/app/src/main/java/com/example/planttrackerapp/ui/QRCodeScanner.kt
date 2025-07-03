package com.example.planttrackerapp.ui

import android.graphics.Rect
import androidx.camera.core.ImageAnalysis
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode


@Composable
fun QRCodeScanner(onQrCodeDetected: (String?) -> Unit) {
    var barcode by remember { mutableStateOf<String?>(null) }
    var boundingBox by remember { mutableStateOf<Rect?>(null) }
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).apply {
                    val options = BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build()
                    val barcodeScanner = BarcodeScanning.getClient(options)

                    cameraController.setImageAnalysisAnalyzer(
                        ContextCompat.getMainExecutor(ctx),
                        MlKitAnalyzer(
                            listOf(barcodeScanner),
                            ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED,
                            ContextCompat.getMainExecutor(ctx)
                        ) { result ->
                            val barcodeResults = result?.getValue(barcodeScanner)
                            if (!barcodeResults.isNullOrEmpty()) {
                                val detectedBarcode = barcodeResults.first()
                                barcode = detectedBarcode.rawValue
                                boundingBox = detectedBarcode.boundingBox
                                onQrCodeDetected(barcode)
                            } else {
                                barcode = null
                                boundingBox = null
                                onQrCodeDetected(null)
                            }
                        }
                    )

                    cameraController.bindToLifecycle(lifecycleOwner)
                    this.controller = cameraController
                }
            }
        )

        boundingBox?.let { box ->
            Canvas(modifier = Modifier.fillMaxSize()) {
                val scaleX = size.width / this.size.width
                val scaleY = size.height / this.size.height

                val left = box.left * scaleX
                val top = box.top * scaleY
                val right = box.right * scaleX
                val bottom = box.bottom * scaleY

                drawRect(
                    color = Color.White,
                    topLeft = Offset(left, top),
                    size = Size(right - left, bottom - top),
                    style = Stroke(width = 4.dp.toPx())
                )
            }
        }
    }
}


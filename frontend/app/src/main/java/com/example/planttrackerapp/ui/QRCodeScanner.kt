package com.example.planttrackerapp.ui


import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.journeyapps.barcodescanner.CaptureActivity

@Composable
fun QRCodeScanner(
    onScanResult: (String?) -> Unit,
    onCancel: () -> Unit
) {
    val qrScanLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // tu dostajemy zeskanowany string
            val scannedResult = result.data?.getStringExtra("SCAN_RESULT")
            // tu coś się dzieje
            onScanResult(scannedResult)
        } else {
            onCancel()
        }
    }

    val scanIntent = Intent().apply {
        setClass(LocalContext.current, CaptureActivity::class.java)
        putExtra("SCAN_MODE", "QR_CODE_MODE")
    }

    LaunchedEffect(Unit) {
        qrScanLauncher.launch(scanIntent)
    }
}

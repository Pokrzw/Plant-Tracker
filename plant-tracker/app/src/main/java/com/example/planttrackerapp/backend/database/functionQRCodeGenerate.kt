package com.example.planttrackerapp.backend.database
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun generateQRCodeAsBase64(content: String): String? {
    try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }

        // Konwersja bitmapy do Base64
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }


}

fun base64ToBitmap(base64Str: String): Bitmap {
    val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}
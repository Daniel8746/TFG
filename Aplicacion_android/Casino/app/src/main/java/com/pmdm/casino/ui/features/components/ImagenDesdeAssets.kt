package com.pmdm.casino.ui.features.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.IOException

@Composable
fun ImagenDesdeAssets(
    modifier: Modifier = Modifier
        .size(100.dp)
        .padding(2.dp), nombreCarpeta: String, nombreArchivo: String
) {
    val context = LocalContext.current

    // Cargar imagen desde assets en un Bitmap
    val bitmap: Bitmap? = remember(nombreCarpeta, nombreArchivo) {
        try {
            val inputStream = context.assets.open("$nombreCarpeta/$nombreArchivo.jpg")
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    bitmap?.let {
        Image(
            painter = BitmapPainter(it.asImageBitmap()),
            contentDescription = "Gato Azul",
            modifier = modifier
        )
    } ?: Text("Imagen no encontrada", color = Color.Red)
}
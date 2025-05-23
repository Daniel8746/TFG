package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun PelotaRuleta(
    size: Size
) {
    val offsetY = animacionDesdeArriba(isDesdeArriba = true, duration = 1000, easing = EaseInOutBounce)

    Canvas(modifier = Modifier.offset { IntOffset(0, offsetY.value.roundToInt()) }) {
        // Define el radio de la pelota
        val radio = 50f

        // Define la posición central de la pelota
        val centroX = size.width / 2
        val centroY = size.height / 2

        // Dibuja el círculo (pelota)
        drawCircle(
            color = Color.Red, // Color de la pelota
            radius = radio, // Radio de la pelota
            center = Offset(centroX, centroY) // Coordenadas del centro
        )
    }
}
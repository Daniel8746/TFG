package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RuletaCompleta(
    modifier: Modifier = Modifier,
    size: Dp,
    brush: Brush,
    degree: Float,
    dibujarHub: Boolean,
    content: @Composable () -> Unit,
) {
    // Dibuja el sector de la ruleta (arco de color correspondiente al número)
    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                drawArc(
                    brush = brush,
                    startAngle = -90f - (degree / 2),
                    sweepAngle = degree,
                    useCenter = true
                )
            }
    ) {
        // Muestra el número centrado en la parte superior del sector
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        ) {
            content()
        }

        // Dibuja el hub central de la ruleta si está habilitado
        if (dibujarHub) {
            Box(
                modifier = Modifier
                    .size(size / 6)
                    .align(Alignment.Center)
                    .background(
                        color = Color(0xFF555555),
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = Color.LightGray,
                        shape = CircleShape
                    )
                    .shadow(elevation = 4.dp, shape = CircleShape)
            )
        }

    }
}
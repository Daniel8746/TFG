package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ApuestaBox(
    modifier: Modifier,
    valor: String,
    color: Color
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (valor == "ROJO" || valor == "NEGRO") {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(if (valor == "ROJO") Color.Red else Color.Black)
            )
        } else Text(text = valor, color = color)
    }
}
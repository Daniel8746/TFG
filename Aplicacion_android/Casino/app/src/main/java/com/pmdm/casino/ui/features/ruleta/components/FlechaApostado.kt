package com.pmdm.casino.ui.features.ruleta.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconoInteractivo(imageVector: ImageVector, descripcion: String, onEvent: () -> Unit) {
    Image(
        modifier = Modifier.clickable(onClick = onEvent),
        imageVector = imageVector,
        contentDescription = descripcion
    )
}
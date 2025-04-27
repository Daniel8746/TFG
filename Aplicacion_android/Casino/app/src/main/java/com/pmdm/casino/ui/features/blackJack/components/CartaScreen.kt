package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun CartaScreen(
    carta: CartaUiState, modifier: Modifier
) {
    val context = LocalContext.current
    val resourceName by remember { mutableStateOf("${carta.palo}_${carta.valor}") } // Construye el nombre de la imagen

    val resourceId = context.resources.getIdentifier("facebook", "drawable", context.packageName)

    Image(
        painter = painterResource(resourceId),
        contentDescription = "imagen carta",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
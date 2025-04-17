package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
fun CartaScreen(carta: CartaUiState) {
    val context = LocalContext.current
    val resourceName = "${carta.palo}_${carta.valor}" // Construye el nombre de la imagen

    val resourceId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)

    Image(
        painter = painterResource(resourceId),
        contentDescription = "imagen carta"
    )
}
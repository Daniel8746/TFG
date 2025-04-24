package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CartaScreen(carta: CartaUiState) {
    val context = LocalContext.current
    val resourceName by remember { mutableStateOf("${carta.palo}_${carta.valor}") } // Construye el nombre de la imagen

    val resourceId = context.resources.getIdentifier("facebook", "drawable", context.packageName)

    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(resourceId),
            contentDescription = "imagen carta",
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.Center)
        )
    }
}
package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlin.random.Random

@Composable
fun CartaScreen(
    carta: CartaUiState, modifier: Modifier
) {
    val carpetaGatos by remember { mutableStateOf(if (Random.nextBoolean()) "gatos_rojos" else "gatos_azules") }
    val resourceName by remember { mutableStateOf("${carta.palo.lowercase()}_${carta.valor.lowercase()}") } // Construye el nombre de la imagen

    ImagenDesdeAssets(
        modifier,
        carpetaGatos,
        resourceName
    )
}
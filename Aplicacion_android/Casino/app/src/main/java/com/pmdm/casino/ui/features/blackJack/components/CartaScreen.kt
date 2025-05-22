@file:JvmName("CartaScreenKt")

package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pmdm.casino.ui.features.blackJack.CartaUiState
import com.pmdm.casino.ui.features.components.ImagenDesdeAssets
import kotlin.random.Random

@Composable
fun ListadoCartas(listadoCartas: List<CartaUiState>) {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    val screenWidthDp: Dp = remember { with(density) { windowInfo.containerSize.width.toDp() } }
    val minCardWidth = remember { 50.dp }
    val maxCardWidth = remember { 150.dp }

    val cardWidth by remember(screenWidthDp, listadoCartas.size) {
        mutableStateOf(
            (screenWidthDp / (listadoCartas.size + 1)).coerceAtLeast(minCardWidth)
                .coerceAtMost(maxCardWidth)
        )
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listadoCartas.forEach { carta ->
            CartaScreen(
                carta,
                Modifier
                    .size(cardWidth)
            )
        }
    }
}

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
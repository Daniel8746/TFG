package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp

@Composable
fun CartasMesa(listadoCartas: List<CartaUiState>) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val minCardWidth by remember { mutableStateOf(50.dp) }
    val maxCardWidth by remember { mutableStateOf(150.dp) }

    val cardWidth by remember(screenWidth, listadoCartas.size) {
        mutableStateOf(
            (screenWidth / (listadoCartas.size + 1)).coerceAtLeast(minCardWidth)
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
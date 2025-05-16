package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun VistaCrupierBlackjack(
    modifier: Modifier,
    listadoCartasMaquina: List<CartaUiState>,
    puntosMaquina: Int
) {
    Column(
        modifier = modifier
    ) {
        CartasMesa(listadoCartasMaquina)

        Text(
            text = "Puntos: $puntosMaquina",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.align(Alignment.Start)
        )
    }
}
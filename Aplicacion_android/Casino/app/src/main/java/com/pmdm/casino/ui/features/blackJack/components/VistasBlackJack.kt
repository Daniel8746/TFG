package com.pmdm.casino.ui.features.blackJack.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.blackJack.CartaUiState
import com.pmdm.casino.ui.features.components.ImagenDesdeAssets

@Composable
fun VistaCrupierBlackjack(
    modifier: Modifier,
    listadoCartasMaquina: List<CartaUiState>,
    puntosMaquina: Int
) {
    Column(
        modifier = modifier
    ) {
        ListadoCartas(listadoCartasMaquina)

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

@Composable
fun VistaJugadorBlackjack(
    modifier: Modifier,
    puntosUsuario: Int,
    listadoCartas: List<CartaUiState>,
    cartaNueva: CartaUiState?
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Puntos: $puntosUsuario",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.align(Alignment.End)
        )

        ListadoCartas(listadoCartas)

        if (cartaNueva != null) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CartaScreen(
                    cartaNueva,
                    Modifier
                        .size(180.dp)
                        .align(Alignment.Center)
                        .padding(top = 5.dp)
                )
            }

        }
    }
}

@Composable
fun DecoracionBlackJack() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            ImagenDesdeAssets(
                nombreCarpeta = "gatos_rojos",
                nombreArchivo = "joker1"
            )

            ImagenDesdeAssets(
                nombreCarpeta = "gatos_azules",
                nombreArchivo = "joker1"
            )
        }

        Row {
            ImagenDesdeAssets(
                nombreCarpeta = "gatos_rojos",
                nombreArchivo = "reversa"
            )

            ImagenDesdeAssets(
                nombreCarpeta = "gatos_azules",
                nombreArchivo = "reversa"
            )
        }

        Column {
            ImagenDesdeAssets(
                nombreCarpeta = "gatos_azules",
                nombreArchivo = "joker2"
            )

            ImagenDesdeAssets(
                nombreCarpeta = "gatos_rojos",
                nombreArchivo = "joker2"
            )
        }
    }
}
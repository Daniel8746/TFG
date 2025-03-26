package com.pmdm.casino.ui.features.casino

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.casino.components.AyudaScreen
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasinoScreen(
    modifier: Modifier = Modifier,
    juegosUiState: List<CasinoUiState>,
    usuarioUiState: UsuarioCasinoUiState,
    onCasinoEvent: (CasinoEvent) -> Unit,
    onBlackJackEvent: (correo: String, saldo: BigDecimal) -> Unit,
    onRuletaEvent: (correo: String, saldo: BigDecimal) -> Unit,
    onTragaMonedas: (correo: String, saldo: BigDecimal) -> Unit,
    onAbrirAyuda: (descripcion: String) -> Unit,
    onCerrarAyuda: () -> Unit,
    isAyudaAbierta: Boolean
) {
    Column {
        TopAppBar(title = {}, actions = {
            // Nombre del usuario
            Text(
                text = usuarioUiState.correo,
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espaciado entre los elementos

            // Saldo del usuario
            Text(
                text = "Saldo: ${usuarioUiState.saldo}",
                modifier = Modifier.align(Alignment.CenterVertically),
                textAlign = TextAlign.End // Alineado a la derecha
            )
        })

        LazyRow {
            items(juegosUiState) {
                ElevatedCard(
                    onClick = {
                        when (it.nombre) {
                            "BlackJack" -> {
                                onCasinoEvent(CasinoEvent.OnBlackJack(onBlackJackEvent))
                            }

                            "Ruleta" -> {
                                onCasinoEvent(CasinoEvent.OnRuleta(onRuletaEvent))
                            }

                            "Traga monedas" -> {
                                onCasinoEvent(CasinoEvent.OnTragaMonedas(onTragaMonedas))
                            }
                        }
                    },
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp,
                        hoveredElevation = 6.dp
                    )
                ) {
                    Column {
                        Text(text = it.nombre, textAlign = TextAlign.Left)
                        Text(text = it.tipo, textAlign = TextAlign.Right)

                        Image(
                            painter = painterResource(R.drawable.login),
                            contentDescription = "Descripción tarjeta"
                        )

                        IconButton(
                            onClick = {onAbrirAyuda(it.reglas)}
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Ayuda"
                            )
                        }
                    }
                }

                if (isAyudaAbierta) {
                    AyudaScreen(it.reglas, onCerrarAyuda)
                }
            }
        }
    }
}
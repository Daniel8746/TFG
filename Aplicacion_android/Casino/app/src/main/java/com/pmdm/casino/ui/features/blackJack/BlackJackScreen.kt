package com.pmdm.casino.ui.features.blackJack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.blackJack.components.CartaScreen
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.TopBar

@Composable
fun BlackJackScreen(
    usuarioUiState: UsuarioCasinoUiState,
    puntosUsuario: Int,
    puntosMaquina: Int,
    finalizarTurnoUsuario: Boolean,
    finalizarTurnoMaquina: Boolean,
    listadoCartas: List<CartaUiState>,
    listadoCartasMaquina: List<CartaUiState>,
    cartaNueva: CartaUiState?,
    onBlackJackEvent: (BlackJackEvent) -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                usuarioUiState = usuarioUiState,
                volverAtras = volverAtras
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Image(
                    painter = painterResource(R.drawable.imagenfondojuegos),
                    contentDescription = "Fondo juegos",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                if (!finalizarTurnoMaquina) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Maquina
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ocupa la mitad superior de la pantalla
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            LazyRow {
                                items(listadoCartasMaquina) { cartaMaquina ->
                                    CartaScreen(cartaMaquina)
                                }
                            }

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

                        // Usuario
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ocupa la mitad inferior de la pantalla
                                .fillMaxWidth()
                                .padding(16.dp)
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

                            LazyRow {
                                items(listadoCartas, key = { "${it.palo}_${it.valor}" }) { carta ->
                                    CartaScreen(carta)
                                }
                            }

                            if (cartaNueva != null) {
                                CartaScreen(
                                    cartaNueva
                                )
                            }
                        }
                    }
                    if (!finalizarTurnoUsuario) {
                        ButtonWithLottie(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(5.dp),
                            text = "Pedir carta",
                            isLoading = false,
                            onClick = {
                                onBlackJackEvent(BlackJackEvent.OnPedirCarta)
                            }
                        )

                        ButtonWithLottie(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(5.dp),
                            text = "Plantarse",
                            isLoading = false,
                            onClick = {
                                onBlackJackEvent(BlackJackEvent.OnPlantarse)
                            }
                        )
                    }
                } else {
                    Column {
                        Text(
                            if ((puntosMaquina in (puntosUsuario + 1)..21) || puntosUsuario > 21) {
                                "Has perdido"
                            } else {
                                "Has ganado"
                            }
                        )

                        Text(
                            "Puntos Jugador: $puntosUsuario"
                        )

                        Text(
                            "Puntos Cruppier: $puntosMaquina"
                        )

                        Row {
                            ButtonWithLottie(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f),
                                text = "Salir",
                                isLoading = false,
                                onClick = volverAtras
                            )

                            ButtonWithLottie(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f),
                                text = "Reiniciar",
                                isLoading = false,
                                onClick = reiniciarPartida
                            )
                        }
                    }
                }
            }
        }
    )
}
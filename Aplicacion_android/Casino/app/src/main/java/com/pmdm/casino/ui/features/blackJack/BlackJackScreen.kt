package com.pmdm.casino.ui.features.blackJack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.R
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.blackJack.components.CartaScreen
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.blackJack.components.CartasMesa
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
    onFinalizarBlackJack: () -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopBar(
                usuarioUiState = usuarioUiState,
                volverAtras = volverAtras,
                onFinalizar = onFinalizarBlackJack
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

                            CartasMesa(listadoCartas)

                            if (cartaNueva != null) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    CartaScreen(
                                        cartaNueva,
                                        Modifier
                                            .size(200.dp)
                                            .align(Alignment.Center)
                                    )
                                }

                            }
                        }
                    }
                    if (!finalizarTurnoUsuario) {
                        ButtonWithLottie(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp),
                            text = "Pedir carta",
                            isLoading = false,
                            onClick = {
                                onBlackJackEvent(BlackJackEvent.OnPedirCarta)
                            }
                        )

                        ButtonWithLottie(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp),
                            text = "Plantarse",
                            isLoading = false,
                            onClick = {
                                onBlackJackEvent(BlackJackEvent.OnPlantarse)
                            }
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            if (puntosUsuario > 21 || (puntosMaquina in puntosUsuario..21)) {
                                "Has perdido"
                            } else {
                                "Has ganado"
                            },
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )

                        Text(
                            "Puntos Jugador: $puntosUsuario",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )

                        Text(
                            "Puntos Cruppier: $puntosMaquina",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth()) {
                            ButtonWithLottie(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .padding(5.dp),
                                text = "Salir",
                                isLoading = false,
                                onClick = {
                                    onFinalizarBlackJack()
                                    volverAtras()
                                }
                            )

                            ButtonWithLottie(
                                modifier = Modifier
                                    .height(50.dp)
                                    .weight(1f)
                                    .padding(5.dp),
                                text = "Reiniciar",
                                isLoading = false,
                                onClick = {
                                    onFinalizarBlackJack()
                                    reiniciarPartida()
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}
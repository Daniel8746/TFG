package com.pmdm.casino.ui.features.blackJack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.blackJack.components.CartaScreen
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.blackJack.components.CartasMesa
import com.pmdm.casino.ui.features.blackJack.components.ImagenDesdeAssets
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI

@Composable
fun BlackJackScreen(
    usuarioUiState: UsuarioCasinoUiState,
    puntosUsuario: Int,
    puntosMaquina: Int,
    reintentarConexion: Boolean,
    errorApi: Boolean,
    finalizarTurnoUsuario: Boolean,
    finalizarTurnoMaquina: Boolean,
    poderPulsarBoton: Boolean,
    listadoCartas: List<CartaUiState>,
    listadoCartasMaquina: List<CartaUiState>,
    cartaNueva: CartaUiState?,
    onBlackJackEvent: (BlackJackEvent) -> Unit,
    onFinalizarBlackJack: () -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit,
    reiniciar: () -> Unit
) {
    FondoBarraCasinoUI(
        usuarioUiState = usuarioUiState,
        reintentarConexion = reintentarConexion,
        errorApi = errorApi,
        reiniciar = reiniciar,
        volverAtras = volverAtras,
        onFinalizarJuego = onFinalizarBlackJack
    ) {
        if (!finalizarTurnoMaquina) {
            Column {
                // Parte superior: Máquina
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(5.dp)
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

                // Parte central Jokers y Reversas
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


                // Parte inferior: Usuario
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp)
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
                                    .size(180.dp)
                                    .align(Alignment.Center)
                                    .padding(top = 5.dp)
                            )
                        }

                    }
                }
            }
            if (!finalizarTurnoUsuario) {
                ButtonWithLottie(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 15.dp, start = 5.dp),
                    text = "Pedir carta",
                    isLoading = false,
                    onClick = {
                        onBlackJackEvent(BlackJackEvent.OnPedirCarta)
                    },
                    enabled = poderPulsarBoton
                )

                ButtonWithLottie(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 15.dp, end = 10.dp),
                    text = "Plantarse",
                    isLoading = false,
                    onClick = {
                        onBlackJackEvent(BlackJackEvent.OnPlantarse)
                    },
                    enabled = poderPulsarBoton
                )
            }
        } else {
            Column(
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
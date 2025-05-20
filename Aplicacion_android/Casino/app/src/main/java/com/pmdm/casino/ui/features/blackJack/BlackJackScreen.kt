package com.pmdm.casino.ui.features.blackJack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.blackJack.components.DecoracionBlackJack
import com.pmdm.casino.ui.features.blackJack.components.FinDePartidaPanel
import com.pmdm.casino.ui.features.blackJack.components.VistaCrupierBlackjack
import com.pmdm.casino.ui.features.blackJack.components.VistaJugadorBlackjack
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoEvent
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoUiState
import java.math.BigDecimal

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
    apuestaUsuario: BigDecimal,
    onBlackJackEvent: (BlackJackEvent) -> Unit,
    onFinalizarBlackJack: () -> Unit,
    volverAtras: () -> Unit,
    reiniciarPartida: () -> Unit,
    reiniciar: () -> Unit,
    onUsuarioEvent: (UsuarioCasinoEvent) -> Unit,
    setEstadoPartida: () -> Unit,
    onApuestaBlackJack: () -> Unit
) {
    FondoBarraCasinoUI(
        usuarioUiState = usuarioUiState,
        reintentarConexion = reintentarConexion,
        errorApi = errorApi,
        reiniciar = reiniciar,
        volverAtras = volverAtras,
    ) {
        if (!finalizarTurnoMaquina) {
            Column {
                // Parte superior: Máquina
                VistaCrupierBlackjack(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(5.dp),
                    listadoCartasMaquina = listadoCartasMaquina,
                    puntosMaquina = puntosMaquina
                )

                // Parte central Jokers y Reversas
                DecoracionBlackJack()

                // Parte inferior: Usuario
                VistaJugadorBlackjack(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp),
                    puntosUsuario = puntosUsuario,
                    listadoCartas = listadoCartas,
                    cartaNueva = cartaNueva
                )
            }
            if (!finalizarTurnoUsuario) {
                ButtonWithLottie(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 15.dp, start = 5.dp),
                    text = "Pedir carta",
                    onClick = {
                        onBlackJackEvent(BlackJackEvent.OnPedirCarta)
                        onApuestaBlackJack()
                    },
                    enabled = poderPulsarBoton
                )

                ButtonWithLottie(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 15.dp, end = 10.dp),
                    text = "Plantarse",
                    onClick = {
                        onBlackJackEvent(BlackJackEvent.OnPlantarse)
                    },
                    enabled = poderPulsarBoton
                )
            }
        } else {
            FinDePartidaPanel(
                puntosUsuario,
                puntosMaquina,
                apuestaUsuario,
                onFinalizarBlackJack,
                volverAtras,
                reiniciarPartida,
                onUsuarioEvent,
                { onBlackJackEvent(BlackJackEvent.OnValueApuestaUsuarioChanged(it.toBigDecimal())) },
                setEstadoPartida
            )
        }
    }
}
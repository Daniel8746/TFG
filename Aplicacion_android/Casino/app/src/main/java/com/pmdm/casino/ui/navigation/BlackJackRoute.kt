package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.apuestas.ApuestasViewModel
import com.pmdm.casino.ui.features.blackJack.BlackJackScreen
import com.pmdm.casino.ui.features.blackJack.BlackJackViewModel
import com.pmdm.casino.ui.features.blackJack.MaquinaViewModel
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel
import kotlinx.serialization.Serializable

@Serializable
object BlackJackRoute

fun NavGraphBuilder.blackDestination(
    onNavegarCasino: () -> Unit,
    vm: BlackJackViewModel,
    vmMaquina: MaquinaViewModel,
    vmApuestas: ApuestasViewModel,
    vmUsuarioCasino: UsuarioCasinoViewModel
) {
    composable<BlackJackRoute> {
        val finalizarTurnoUsuario = vm.finalizarPartida

        LaunchedEffect(finalizarTurnoUsuario) {
            if (finalizarTurnoUsuario) {
                vmMaquina.empezarTurnoMaquina()
            }
        }

        val context = LocalContext.current

        BlackJackScreen(
            usuarioUiState = vmUsuarioCasino.usuarioCasinoUiState,
            puntosUsuario = vm.puntosTotalesUsuario,
            puntosMaquina = vmMaquina.puntosTotalesMaquina,
            reintentarConexion = vm.reintentarConexion,
            errorApi = vm.errorApi,
            finalizarTurnoUsuario = vm.finalizarPartida,
            finalizarTurnoMaquina = vmMaquina.finalizarPartida,
            poderPulsarBoton = vm.poderPulsarBoton,
            listadoCartas = vm.cartasUiState.collectAsState().value.toList(),
            listadoCartasMaquina = vmMaquina.cartasUiState.collectAsState().value.toList(),
            cartaNueva = vm.cartaRecienteUiState.collectAsState().value,
            onBlackJackEvent = { vm.onBlackJackEvent(it) },
            onFinalizarBlackJack = {
                vm.reiniciarCartas()
                vmApuestas.finalizarBlackJack()
            },
            volverAtras = {
                onNavegarCasino()
            },
            reiniciarPartida = {
                vm.reiniciarPartida()
                vmMaquina.reiniciarPartida()
            },
            reiniciar = { vm.reiniciar(context) }
        )
    }
}
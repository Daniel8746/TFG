package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.apuestas.ApuestasViewModel
import com.pmdm.casino.ui.features.blackJack.BlackJackScreen
import com.pmdm.casino.ui.features.blackJack.BlackJackViewModel
import com.pmdm.casino.ui.features.blackJack.MaquinaViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class BlackJackRoute(val correo: String, val saldo: @Contextual BigDecimal)

fun NavGraphBuilder.blackDestination(
    onNavegarCasino: (correo: String, saldo: BigDecimal) -> Unit
) {
    composable(
        route = "black_jack/{correo}/{saldo}",
        arguments = listOf(
            navArgument("correo") { type = NavType.StringType },
            navArgument("saldo") { type = BigDecimalNavType() }
        )
    ) { backStackEntry ->
        val vm = hiltViewModel<BlackJackViewModel>(backStackEntry)
        val vmMaquina = hiltViewModel<MaquinaViewModel>(backStackEntry)
        val vmApuestas = hiltViewModel<ApuestasViewModel>(backStackEntry)

        val usuarioCasino: BlackJackRoute = remember { backStackEntry.toRoute<BlackJackRoute>() }

        vm.crearUsuarioCasino(usuarioCasino)

        val finalizarTurnoUsuario = vm.finalizarPartida

        LaunchedEffect(finalizarTurnoUsuario) {
            if (finalizarTurnoUsuario) {
                vmMaquina.empezarTurnoMaquina(vm.puntosTotalesUsuario)
            }
        }

        BlackJackScreen(
            usuarioUiState = vm.usuarioUiState,
            puntosUsuario = vm.puntosTotalesUsuario,
            puntosMaquina = vmMaquina.puntosTotalesMaquina,
            finalizarTurnoUsuario = vm.finalizarPartida,
            finalizarTurnoMaquina = vmMaquina.finalizarPartida,
            listadoCartas = vm.cartasUiState.collectAsState().value,
            listadoCartasMaquina = vmMaquina.cartasUiState.collectAsState().value,
            cartaNueva = vm.cartaRecienteUiState.collectAsState().value,
            onBlackJackEvent = { vm.onBlackJackEvent(it) },
            onFinalizarBlackJack = { vmApuestas.finalizarBlackJack() },
            volverAtras = { onNavegarCasino(vm.usuarioUiState.correo, vm.usuarioUiState.saldo) },
            reiniciarPartida = {
                vm.reiniciarPartida()
                vmMaquina.reiniciarPartida()
            }
        )
    }
}
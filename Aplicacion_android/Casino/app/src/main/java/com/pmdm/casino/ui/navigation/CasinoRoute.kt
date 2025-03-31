package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.casino.CasinoScreen
import com.pmdm.casino.ui.features.casino.CasinoViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CasinoRoute(val correo: String, val saldo: @Contextual BigDecimal)

fun NavGraphBuilder.casinoDestination(
    onNavegarBlackJack: (correo: String, saldo: BigDecimal) -> Unit,
    onNavegarRuleta: (correo: String, saldo: BigDecimal) -> Unit,
    onNavegarTragaMonedas: (correo: String, saldo: BigDecimal) -> Unit
) {
    composable(
        route = "casino/{correo}/{saldo}",
        arguments = listOf(
            navArgument("correo") { type = NavType.StringType },
            navArgument("saldo") { type = BigDecimalNavType() }
        )
    ) {
        val usuarioCasino : CasinoRoute = remember { it.toRoute<CasinoRoute>() }
        val vm = hiltViewModel<CasinoViewModel>()

        vm.crearUsuarioCasino(usuarioCasino)

        CasinoScreen(
            juegosUiState = vm.juegosUiState,
            usuarioUiState = vm.usuarioUiState,
            onCasinoEvent = { vm.onCasinoEvent(it) },
            onBlackJackEvent = onNavegarBlackJack,
            onRuletaEvent = onNavegarRuleta,
            onTragaMonedas = onNavegarTragaMonedas,
            onAyudaEvent = { vm.onAbrirAyuda() },
            isAyudaAbierta = vm.isAyudaAbierta
        )
    }
}
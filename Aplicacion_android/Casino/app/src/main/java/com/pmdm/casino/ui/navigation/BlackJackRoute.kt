package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.blackJack.BlackJackScreen
import com.pmdm.casino.ui.features.blackJack.BlackJackViewModel
import com.pmdm.casino.ui.features.casino.CasinoScreen
import com.pmdm.casino.ui.features.casino.CasinoViewModel
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
    ) {
        val usuarioCasino : BlackJackRoute = remember { it.toRoute<BlackJackRoute>() }
        val vm = hiltViewModel<BlackJackViewModel>()

        vm.crearUsuarioCasino(usuarioCasino)

        BlackJackScreen(
            usuarioUiState = vm.usuarioUiState
        )
    }
}
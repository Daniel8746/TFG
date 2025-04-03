package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.tragaMonedas.TragaMonedasScreen
import com.pmdm.casino.ui.features.tragaMonedas.TragaMonedasViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class TragaMonedasRoute(val correo: String, val saldo: @Contextual BigDecimal)

fun NavGraphBuilder.tragaDestination(
    onNavegarCasino: (correo: String, saldo: BigDecimal) -> Unit
) {
    composable(
        route = "traga_monedas/{correo}/{saldo}",
        arguments = listOf(
            navArgument("correo") { type = NavType.StringType },
            navArgument("saldo") { type = BigDecimalNavType() }
        )
    ) {
        val usuarioCasino : TragaMonedasRoute = remember { it.toRoute<TragaMonedasRoute>() }
        val vm = hiltViewModel<TragaMonedasViewModel>()

        vm.crearUsuarioCasino(usuarioCasino)

        TragaMonedasScreen(

        )
    }
}
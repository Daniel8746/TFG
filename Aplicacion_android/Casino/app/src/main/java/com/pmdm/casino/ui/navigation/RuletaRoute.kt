package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.ruleta.RuletaScreen
import com.pmdm.casino.ui.features.ruleta.RuletaViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class RuletaRoute(val correo: String, val saldo: @Contextual BigDecimal)

fun NavGraphBuilder.ruletaDestination(
    onNavegarCasino: (correo: String, saldo: BigDecimal) -> Unit
) {
    composable(
        route = "ruleta/{correo}/{saldo}",
        arguments = listOf(
            navArgument("correo") { type = NavType.StringType },
            navArgument("saldo") { type = BigDecimalNavType() }
        )
    ) { backStackEntry ->
        val vm = hiltViewModel<RuletaViewModel>(backStackEntry)

        val usuarioCasino: RuletaRoute = remember { backStackEntry.toRoute<RuletaRoute>() }

        vm.crearUsuarioCasino(usuarioCasino)

        RuletaScreen(

        )
    }
}
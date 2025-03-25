package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.casino.CasinoScreen
import com.pmdm.casino.ui.features.casino.CasinoViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CasinoRoute(val correo: String, val saldo: @Contextual BigDecimal)

fun NavGraphBuilder.casinoDestination() {
    composable<CasinoRoute> {
        val usuarioCasino : CasinoRoute = remember { it.toRoute<CasinoRoute>() }
        val vm = hiltViewModel<CasinoViewModel>()

        vm.crearUsuarioCasino(usuarioCasino)

        CasinoScreen()
    }
}
package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pmdm.casino.ui.features.casino.CasinoScreen
import kotlinx.serialization.Serializable

@Serializable
data class CasinoRoute(val correo: String)

fun NavGraphBuilder.casinoDestination() {
    composable<CasinoRoute> {
        val correo : CasinoRoute = remember { it.toRoute<CasinoRoute>() }
        CasinoScreen()
    }
}
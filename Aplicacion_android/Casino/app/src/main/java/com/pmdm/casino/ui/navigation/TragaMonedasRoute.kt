package com.pmdm.casino.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.tragaMonedas.TragaMonedasScreen
import com.pmdm.casino.ui.features.tragaMonedas.TragaMonedasViewModel
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel
import kotlinx.serialization.Serializable

@Serializable
object TragaMonedasRoute

fun NavGraphBuilder.tragaDestination(
    onNavegarCasino: () -> Unit,
    vm: TragaMonedasViewModel,
    vmUsuarioCasino: UsuarioCasinoViewModel
) {
    composable<TragaMonedasRoute> {
        TragaMonedasScreen(

        )
    }
}
package com.pmdm.casino.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.splashScreen.SplashScreen
import com.pmdm.casino.ui.features.splashScreen.SplashViewModel
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
object SplashRoute

fun NavGraphBuilder.splashDestination(
    onNavegarLogin: () -> Unit,
    onNavegarJuegos: (correo: String, saldo: BigDecimal) -> Unit
) {
    composable<SplashRoute> {
        val vm = hiltViewModel<SplashViewModel>()

        vm.comprobarToken()

        SplashScreen(
            onNavegarLogin,
            onNavegarJuegos,
            vm.correoState.value,
            vm.saldoState.value
        )
    }
}
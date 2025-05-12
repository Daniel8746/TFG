package com.pmdm.casino.ui.navigation

import androidx.compose.ui.platform.LocalContext
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

        val context = LocalContext.current

        SplashScreen(
            correo = vm.correoState.value,
            saldo = vm.saldoState.value,
            errorApi = vm.errorApi,
            reintentarConexion = vm.reintentarConexion,
            reiniciar = { vm.reiniciar(context) },
            onNavegarLogin = onNavegarLogin,
            onNavegarJuegos = onNavegarJuegos
        )
    }
}
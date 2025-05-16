package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.login.LoginScreen
import com.pmdm.casino.ui.features.login.LoginViewModel
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel
import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

fun NavGraphBuilder.loginDestination(
    onNavegarNuevaCuenta: () -> Unit,
    onNavegarCasino: () -> Unit,
    vmUsuarioCasino: UsuarioCasinoViewModel
) {
    composable<LoginRoute> {
        val vm = hiltViewModel<LoginViewModel>()

        val context = LocalContext.current

        LoginScreen(
            usuarioUiState = vm.usuarioUiState,
            validacionLoginUiState = vm.validacionLoginUiState,
            loginErroneo = vm.usuarioCorrecto.collectAsState().value,
            isLoading = vm.isLoading.collectAsState().value,
            recordarmeState = vm.recordarmeState,
            reintentarConexion = vm.reintentarConexion,
            errorApi = vm.errorApi,
            onLoginEvent = { vm.onLoginEvent(it) },
            onNavigateToCasino = {
                vmUsuarioCasino.actualizarUsuarioCasino(
                    correo = vm.usuarioUiState.login,
                    saldo = vm.saldo.value
                )
                onNavegarCasino()
            },
            onNavigateToNuevaCuenta = onNavegarNuevaCuenta,
            onRecordarmeState = { vm.onRecordarmeState(it) },
            reiniciar = { vm.reiniciar(context) }
        )
    }
}
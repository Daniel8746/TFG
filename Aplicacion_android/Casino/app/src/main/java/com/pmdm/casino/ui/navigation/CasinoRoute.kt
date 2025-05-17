package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.casino.CasinoScreen
import com.pmdm.casino.ui.features.casino.JuegosViewModel
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel
import kotlinx.serialization.Serializable

@Serializable
object CasinoRoute

fun NavGraphBuilder.casinoDestination(
    onNavegarBlackJack: () -> Unit,
    onNavegarRuleta: () -> Unit,
    onNavegarTragaMonedas: () -> Unit,
    vmUsuarioCasino: UsuarioCasinoViewModel
) {
    composable<CasinoRoute> {
        val vm = hiltViewModel<JuegosViewModel>()

        val context = LocalContext.current

        CasinoScreen(
            juegosUiState = vm.juegosUiState.collectAsState().value,
            usuarioUiState = vmUsuarioCasino.usuarioCasinoUiState,
            onCasinoEvent = { vm.onCasinoEvent(it) },
            onBlackJackEvent = onNavegarBlackJack,
            onRuletaEvent = onNavegarRuleta,
            onTragaMonedas = onNavegarTragaMonedas,
            onAyudaEvent = vm::onAbrirAyuda,
            isAyudaAbierta = vm.isAyudaAbierta,
            reintentarConexion = vm.reintentarConexion,
            errorApi = vm.errorApi,
            reiniciar = { vm.reiniciar(context) }
        )
    }
}
package com.pmdm.casino.ui.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.ruleta.RuletaScreen
import com.pmdm.casino.ui.features.ruleta.RuletaViewModel
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel
import kotlinx.serialization.Serializable

@Serializable
object RuletaRoute

fun NavGraphBuilder.ruletaDestination(
    onNavegarCasino: () -> Unit,
    vm: RuletaViewModel,
    vmUsuarioCasino: UsuarioCasinoViewModel
) {
    composable<RuletaRoute> {
        val context = LocalContext.current

        RuletaScreen(
            poderPulsarBoton = vm.poderPulsarBoton,
            valorState = vm.apostado,
            usuarioUiState = vmUsuarioCasino.usuarioCasinoUiState,
            reintentarConexion = vm.reintentarConexion,
            errorApi = vm.errorApi,
            listaNumerosApostar = vm.listaNumerosApostar,
            reiniciar = { vm.reiniciar(context) },
            volverAtras = {
                onNavegarCasino()
            },
            onRuletaEvent = { vm.onRuletaEvent(it) }
        )
    }
}
package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.ruleta.ApuestasUiState
import com.pmdm.casino.ui.features.ruleta.RuletaScreen
import com.pmdm.casino.ui.features.ruleta.RuletaViewModel
import com.pmdm.casino.ui.features.ruleta.TipoApuestaEnum
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoEvent
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

        val esRojo = setOf(
            1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
        )

        val bloques = (1..36).map {
            ApuestasUiState(
                it.toString(),
                if (esRojo.contains(it)) Color.Red else Color.Black,
                TipoApuestaEnum.NUMERO
            )
        }.chunked(3).chunked(4)

        RuletaScreen(
            tiempo = vm.tiempoContador.collectAsState().value,
            apuestaUsuario = vm.apostado,
            usuarioUiState = vmUsuarioCasino.usuarioCasinoUiState,
            reintentarConexion = vm.reintentarConexion,
            errorApi = vm.errorApi,
            listaApuestaMarcado = vm.listaApuestaMarcado,
            listaApuestaDefinitiva = vm.listaApuestaDefinitiva.collectAsState().value.keys,
            enabled = vm.tiempoContador.collectAsState().value > 0,
            listaNumerosRojos = esRojo,
            listaNumeros = bloques,
            reiniciar = { vm.reiniciar(context) },
            volverAtras = {
                onNavegarCasino()
            },
            onRuletaEvent = { vm.onRuletaEvent(it) },
            onAumentarSaldoUsuario = {
                vmUsuarioCasino.onUsuarioCasinoEvent(
                    UsuarioCasinoEvent.AumentarSaldo(
                        vm.listaApuestaDefinitiva.value.filter {
                            vm.listaApuestaMarcado.contains(it.key)
                        }.values.sumOf { it }
                    )
                )
            },
            onBajarSaldoUsuario = {
                vmUsuarioCasino.onUsuarioCasinoEvent(
                    UsuarioCasinoEvent.BajarSaldo(
                        vm.listaApuestaDefinitiva.value.filter {
                            vm.listaApuestaMarcado.contains(it.key)
                        }.values.sumOf { it }
                    )
                )
            }
        )
    }
}
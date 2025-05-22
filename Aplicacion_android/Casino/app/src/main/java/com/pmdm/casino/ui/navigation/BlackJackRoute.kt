package com.pmdm.casino.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmdm.casino.ui.features.apuestas.ApuestasViewModel
import com.pmdm.casino.ui.features.blackJack.BlackJackScreen
import com.pmdm.casino.ui.features.blackJack.BlackJackViewModel
import com.pmdm.casino.ui.features.blackJack.DetallesBlackJack
import com.pmdm.casino.ui.features.blackJack.MaquinaViewModel
import com.pmdm.casino.ui.features.evaluarResultado
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoEvent
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoViewModel
import kotlinx.serialization.Serializable

@Serializable
object BlackJackRoute

fun NavGraphBuilder.blackDestination(
    onNavegarCasino: () -> Unit,
    vm: BlackJackViewModel,
    vmMaquina: MaquinaViewModel,
    vmApuestas: ApuestasViewModel,
    vmUsuarioCasino: UsuarioCasinoViewModel
) {
    composable<BlackJackRoute> {
        val finalizarTurnoUsuario = vm.finalizarPartida

        LaunchedEffect(finalizarTurnoUsuario) {
            if (finalizarTurnoUsuario) {
                if (vm.puntosTotalesUsuario < 21) {
                    vmMaquina.empezarTurnoMaquina {
                        vmApuestas.apuestaJuegoBlackJack(
                            correo = vmUsuarioCasino.usuarioCasinoUiState.correo,
                            nombreJuego = "Blackjack Europeo",
                            montoApostado = vm.apuestaUsuario,
                            resultado = "En curso",
                            detalles = DetallesBlackJack(
                                puntosUsuario = vm.puntosTotalesUsuario,
                                puntosCrupier = vmMaquina.puntosTotalesMaquina,
                                cartasUsuario = vm.cartasUiState.value,
                                cartasCrupier = vmMaquina.cartasUiState.value
                            )
                        )
                    }
                } else {
                    vmApuestas.finalizarBlackJack(
                        resultado = evaluarResultado(
                            puntosUsuario = vm.puntosTotalesUsuario,
                            puntosMaquina = vmMaquina.puntosTotalesMaquina
                        ),
                        detalles = DetallesBlackJack(
                            puntosUsuario = vm.puntosTotalesUsuario,
                            puntosCrupier = vmMaquina.puntosTotalesMaquina,
                            cartasUsuario = vm.cartasUiState.value,
                            cartasCrupier = vmMaquina.cartasUiState.value
                        )
                    )
                    vmMaquina.plantarse()
                }
            }
        }

        val context = LocalContext.current

        BlackJackScreen(
            usuarioUiState = vmUsuarioCasino.usuarioCasinoUiState,
            puntosUsuario = vm.puntosTotalesUsuario,
            puntosMaquina = vmMaquina.puntosTotalesMaquina,
            reintentarConexion = vm.reintentarConexion,
            errorApi = vm.errorApi,
            finalizarTurnoUsuario = vm.finalizarPartida,
            finalizarTurnoMaquina = vmMaquina.finalizarPartida,
            poderPulsarBoton = vm.poderPulsarBoton,
            listadoCartas = vm.cartasUiState.collectAsState().value.toList(),
            listadoCartasMaquina = vmMaquina.cartasUiState.collectAsState().value.toList(),
            cartaNueva = vm.cartaRecienteUiState.collectAsState().value,
            apuestaUsuario = vm.apuestaUsuario,
            onBlackJackEvent = { vm.onBlackJackEvent(it) },
            onFinalizarBlackJack = {
                vmApuestas.finalizarBlackJack(
                    resultado = evaluarResultado(
                        puntosUsuario = vm.puntosTotalesUsuario,
                        puntosMaquina = vmMaquina.puntosTotalesMaquina
                    ),
                    detalles = DetallesBlackJack(
                        puntosUsuario = vm.puntosTotalesUsuario,
                        puntosCrupier = vmMaquina.puntosTotalesMaquina,
                        cartasUsuario = vm.cartasUiState.value,
                        cartasCrupier = vmMaquina.cartasUiState.value
                    )
                )
                vm.reiniciarCartas()
            },
            volverAtras = {
                onNavegarCasino()
            },
            setEstadoPartida = {
                vmUsuarioCasino.setEstadoPartida("Blackjack", false)
            },
            reiniciarPartida = {
                vmUsuarioCasino.setEstadoPartida("Blackjack", false)
                vmUsuarioCasino.onUsuarioCasinoEvent(UsuarioCasinoEvent.BajarSaldoBlackJack(vm.apuestaUsuario))
                vmUsuarioCasino.setEstadoPartida("Blackjack", true)
                vm.reiniciarPartida()
                vmMaquina.reiniciarPartida()
            },
            reiniciar = { vm.reiniciar(context) },
            onUsuarioEvent = { vmUsuarioCasino.onUsuarioCasinoEvent(it) },
            onApuestaBlackJack = {
                vmApuestas.apuestaJuegoBlackJack(
                    correo = vmUsuarioCasino.usuarioCasinoUiState.correo,
                    nombreJuego = "Blackjack Europeo",
                    montoApostado = vm.apuestaUsuario,
                    resultado = "En curso",
                    detalles = DetallesBlackJack(
                        puntosUsuario = vm.puntosTotalesUsuario,
                        puntosCrupier = vmMaquina.puntosTotalesMaquina,
                        cartasUsuario = vm.cartasUiState.value,
                        cartasCrupier = vmMaquina.cartasUiState.value
                    )
                )
            }
        )
    }
}
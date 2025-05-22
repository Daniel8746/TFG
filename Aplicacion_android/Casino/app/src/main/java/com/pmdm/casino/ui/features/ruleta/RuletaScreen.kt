package com.pmdm.casino.ui.features.ruleta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI
import com.pmdm.casino.ui.features.ruleta.components.TableroRuleta
import com.pmdm.casino.ui.features.ruleta.components.VistaJugadorRuleta
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoUiState
import java.math.BigDecimal

@Composable
fun RuletaScreen(
    poderPulsarBoton: Boolean,
    apuestaUsuario: BigDecimal,
    usuarioUiState: UsuarioCasinoUiState,
    reintentarConexion: Boolean,
    errorApi: Boolean,
    listaApuestaMarcado: List<ApuestasUiState>,
    listaApuestaDefinitiva: Set<ApuestasUiState>,
    reiniciar: () -> Unit,
    volverAtras: () -> Unit,
    onRuletaEvent: (RuletaEvent) -> Unit,
) {
    FondoBarraCasinoUI(
        usuarioUiState = usuarioUiState,
        reintentarConexion = reintentarConexion,
        errorApi = errorApi,
        reiniciar = reiniciar,
        volverAtras = volverAtras
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Se necesita una ruleta para poner arriba

            // Tablero de los números y apuestas especiales
            TableroRuleta(
                listaApuestaMarcado = listaApuestaMarcado,
                listaApuestaDefinitiva = listaApuestaDefinitiva,
                onRuletaEvent = onRuletaEvent
            )

            // Parte del usuario para apostar
            VistaJugadorRuleta(
                onRuletaEvent = onRuletaEvent,
                apuestaUsuario = apuestaUsuario,
                poderPulsarBoton = poderPulsarBoton,
                saldo = usuarioUiState.saldo
            )
        }
    }
}
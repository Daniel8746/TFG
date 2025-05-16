package com.pmdm.casino.ui.features.ruleta

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEntero
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI
import com.pmdm.casino.ui.features.ruleta.components.IconoInteractivo
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoUiState

@Composable
fun RuletaScreen(
    poderPulsarBoton: Boolean,
    valorState: Int,
    usuarioUiState: UsuarioCasinoUiState,
    reintentarConexion: Boolean,
    errorApi: Boolean,
    reiniciar: () -> Unit,
    volverAtras: () -> Unit,
    onRuletaEvent: (RuletaEvent) -> Unit,
) {
    val numeros = remember { (0..36).map { it.toString() } }
    val filas = remember { numeros.chunked(3) }
    val esRojo = remember {
        setOf(
            1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
        )
    }

    FondoBarraCasinoUI(
        usuarioUiState = usuarioUiState,
        reintentarConexion = reintentarConexion,
        errorApi = errorApi,
        reiniciar = reiniciar,
        volverAtras = volverAtras,
        onFinalizarJuego = {}
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Se necesita una ruleta para poner arriba


            filas.forEach { fila ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    fila.forEach { numero ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    when {
                                        numero == "0" -> Color.Green
                                        esRojo.contains(numero.toInt()) -> Color.Red
                                        else -> Color.Black
                                    }
                                )
                                .clickable(onClick = {
                                    onRuletaEvent(
                                        RuletaEvent.ApuestaSeleccionada(
                                            TipoApuestaEnum.NUMERO,
                                            numero
                                        )
                                    )
                                })
                        ) {
                            Text(text = numero, color = Color.White)
                        }
                    }
                }
            }

            // Poner cuanto apostar
            Column {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconoInteractivo(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        descripcion = "Flecha bajar precio",
                        onEvent = { onRuletaEvent(RuletaEvent.FlechaBajarPulsado) }
                    )

                    OutlinedTextFieldEntero(
                        label = "",
                        valorState = valorState,
                        validacionState = object : Validacion {},
                        onValueChange = { onRuletaEvent(RuletaEvent.ApuestaChanged(it)) }
                    )

                    IconoInteractivo(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        descripcion = "Flecha subir precio",
                        onEvent = { onRuletaEvent(RuletaEvent.FlechaSubirPulsado) },
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    ButtonWithLottie(
                        text = "Apostar",
                        onClick = { onRuletaEvent(RuletaEvent.Apostar) },
                        enabled = poderPulsarBoton,
                    )

                    ButtonWithLottie(
                        text = "Quitar apuesta",
                        onClick = { onRuletaEvent(RuletaEvent.QuitarApuesta) },
                        enabled = poderPulsarBoton,
                    )
                }
            }
        }
    }
}
package com.pmdm.casino.ui.features.ruleta

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldEntero
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldPhone
import com.github.pmdmiesbalmis.components.ui.composables.OutlinedTextFieldReal
import com.github.pmdmiesbalmis.components.validacion.Validacion
import com.pmdm.casino.ui.features.components.ButtonWithLottie
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI
import com.pmdm.casino.ui.features.ruleta.components.ApuestaBox
import com.pmdm.casino.ui.features.ruleta.components.IconoInteractivo
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoUiState

@Composable
fun RuletaScreen(
    poderPulsarBoton: Boolean,
    valorState: Double,
    usuarioUiState: UsuarioCasinoUiState,
    reintentarConexion: Boolean,
    errorApi: Boolean,
    listaNumerosApostar: List<String>,
    reiniciar: () -> Unit,
    volverAtras: () -> Unit,
    onRuletaEvent: (RuletaEvent) -> Unit,
) {
    val bloques = (1..36).map { ApuestasUiState(TipoApuestaEnum.NUMERO, it.toString()) }
        .chunked(3).chunked(4)

    val apuestasUiStateDocenas = listOf(
        ApuestasUiState(TipoApuestaEnum.DOCENA1, "1ª Docena"),
        ApuestasUiState(TipoApuestaEnum.DOCENA2, "2ª Docena"),
        ApuestasUiState(TipoApuestaEnum.DOCENA3, "3ª Docena")
    )

    val apuestasUiStateEspeciales = listOf(
        listOf(
            ApuestasUiState(TipoApuestaEnum.MITAD1, "1-18"),
            ApuestasUiState(TipoApuestaEnum.PAR, "PAR")

        ),
        listOf(
            ApuestasUiState(TipoApuestaEnum.NEGRO, "NEGRO"),
            ApuestasUiState(TipoApuestaEnum.ROJO, "ROJO")
        ),
        listOf(
            ApuestasUiState(TipoApuestaEnum.IMPAR, "IMPAR"),
            ApuestasUiState(TipoApuestaEnum.MITAD2, "19-36")
        )
    )

    val apuestasUiStateFilas = listOf(
        ApuestasUiState(TipoApuestaEnum.FILA1, "2 a 1"),
        ApuestasUiState(TipoApuestaEnum.FILA2, "2 a 1"),
        ApuestasUiState(TipoApuestaEnum.FILA3, "2 a 1")
    )

    val esRojo = setOf(
        1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
    )

    val width = 40.dp
    val height = 40.dp

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


            Column {
                // Ocupa 3 espacios el 0
                // Fila del "0"
                ApuestaBox(
                    modifier = Modifier
                        .size(width = width * 3, height = height)
                        .border(
                            1.dp,
                            if (listaNumerosApostar.contains("0")) Color.Magenta
                            else Color.Yellow
                        )
                        .background(Color.Green)
                        .clickable(
                            onClick = {
                                onRuletaEvent(
                                    RuletaEvent.ApuestaSeleccionada(
                                        ApuestasUiState(
                                            TipoApuestaEnum.NUMERO,
                                            "0"
                                        )
                                    )
                                )
                            }
                        ),
                    valor = "0",
                    color = Color.White
                )

                // Números
                bloques.forEachIndexed { index, filas ->
                    Row {
                        Column {
                            // Fila de los números
                            filas.forEach { fila ->
                                Row {
                                    fila.forEach { numero ->
                                        ApuestaBox(
                                            modifier = Modifier
                                                .size(width = width, height = height)
                                                .border(
                                                    1.dp,
                                                    if (listaNumerosApostar.contains(numero.valor)) Color.Magenta
                                                    else Color.Yellow
                                                )
                                                .background(
                                                    if (esRojo.contains(numero.valor.toInt())) Color.Red
                                                    else Color.Black
                                                )
                                                .clickable(
                                                    onClick = {
                                                        onRuletaEvent(
                                                            RuletaEvent.ApuestaSeleccionada(
                                                                numero
                                                            )
                                                        )
                                                    }
                                                ),
                                            valor = numero.valor,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        // Docenas
                        ApuestaBox(modifier = Modifier
                            .size(width = width + 40.dp, height = height * 4)
                            .border(
                                1.dp,
                                if (listaNumerosApostar.contains(apuestasUiStateDocenas[index].valor)) Color.Magenta
                                else Color.Yellow
                            )
                            .background(Color.LightGray)
                            .clickable(
                                onClick = {
                                    onRuletaEvent(
                                        RuletaEvent.ApuestaSeleccionada(
                                            apuestasUiStateDocenas[index]
                                        )
                                    )
                                }
                            ),
                            valor = apuestasUiStateDocenas[index].valor,
                            color = Color.Black
                        )

                        // Apuestas especiales debajo de docena
                        Column {
                            apuestasUiStateEspeciales[index].forEach { especial ->
                                ApuestaBox(
                                    modifier = Modifier
                                        .size(width = width + 10.dp, height = height * 2)
                                        .border(
                                            1.dp,
                                            if (listaNumerosApostar.contains(especial.valor)) Color.Magenta
                                            else Color.Yellow
                                        )
                                        .background(Color.LightGray)
                                        .clickable(
                                            onClick = {
                                                onRuletaEvent(
                                                    RuletaEvent.ApuestaSeleccionada(
                                                        especial
                                                    )
                                                )
                                            }
                                        ),
                                    valor = especial.valor,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                // Apuestas especiales por columnas
                Row {
                    apuestasUiStateFilas.forEach { apuestaFila ->
                        ApuestaBox(
                            modifier = Modifier
                                .size(width = width, height = height)
                                .border(
                                    1.dp,
                                    if (listaNumerosApostar.contains(apuestaFila.valor)) Color.Magenta
                                    else Color.Yellow
                                )
                                .background(Color.LightGray)
                                .clickable(
                                    onClick = {
                                        onRuletaEvent(RuletaEvent.ApuestaSeleccionada(apuestaFila))
                                    }
                                ),
                            valor = apuestaFila.valor,
                            color = Color.White
                        )
                    }
                }
            }

            // Poner cuanto apostar
            Column {
                Row {
                    IconoInteractivo(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        descripcion = "Flecha bajar precio",
                        onEvent = { onRuletaEvent(RuletaEvent.FlechaBajarPulsado) }
                    )

                    OutlinedTextFieldReal(
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
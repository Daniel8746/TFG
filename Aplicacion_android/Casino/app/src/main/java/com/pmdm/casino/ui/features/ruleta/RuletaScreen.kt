package com.pmdm.casino.ui.features.ruleta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI

@Composable
fun RuletaScreen(
    usuarioUiState: UsuarioCasinoUiState,
    reintentarConexion: Boolean,
    reiniciar: () -> Unit,
    volverAtras: () -> Unit
) {
    val numeros by remember { mutableStateOf((0..36).map { it.toString() }) }
    val filas by remember { mutableStateOf(numeros.chunked(3)) }
    val esRojo by remember {
        mutableStateOf(
            setOf(
                1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36
            )
        )
    }

    FondoBarraCasinoUI(
        usuarioUiState = usuarioUiState,
        reintentarConexion = reintentarConexion,
        reiniciar = reiniciar,
        volverAtras = volverAtras,
        onFinalizarJuego = {}
    ) {
        // Se necesita una ruleta para poner arriba

        // Ejemplo ruleta arriba numeros abajo para seleccionar
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
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
                        ) {
                            Text(text = numero, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
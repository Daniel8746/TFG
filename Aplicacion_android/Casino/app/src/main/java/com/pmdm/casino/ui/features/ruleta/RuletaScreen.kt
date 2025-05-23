package com.pmdm.casino.ui.features.ruleta

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmdm.casino.ui.features.components.FondoBarraCasinoUI
import com.pmdm.casino.ui.features.ruleta.components.RuletaCompleta
import com.pmdm.casino.ui.features.ruleta.components.TableroRuleta
import com.pmdm.casino.ui.features.ruleta.components.VistaJugadorRuleta
import com.pmdm.casino.ui.features.usuarioCasino.UsuarioCasinoUiState
import java.math.BigDecimal

@Composable
fun RuletaScreen(
    tiempo: Int,
    apuestaUsuario: BigDecimal,
    usuarioUiState: UsuarioCasinoUiState,
    reintentarConexion: Boolean,
    errorApi: Boolean,
    enabled: Boolean,
    listaApuestaMarcado: List<ApuestasUiState>,
    listaApuestaDefinitiva: Set<ApuestasUiState>,
    listaNumerosRojos: Set<Int>,
    listaNumeros: List<List<List<ApuestasUiState>>>,
    reiniciar: () -> Unit,
    volverAtras: () -> Unit,
    onRuletaEvent: (RuletaEvent) -> Unit,
    onAumentarSaldoUsuario: () -> Unit,
    onBajarSaldoUsuario: () -> Unit
) {
    val items = remember {
        listOf(
            ApuestasUiState("0", Color.Green, TipoApuestaEnum.NUMERO),
            ApuestasUiState("32", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("15", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("19", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("4", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("21", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("2", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("25", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("17", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("34", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("6", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("27", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("13", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("36", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("11", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("30", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("8", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("23", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("10", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("5", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("24", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("16", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("33", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("1", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("20", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("14", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("31", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("9", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("22", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("18", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("29", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("7", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("28", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("12", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("35", Color.Black, TipoApuestaEnum.NUMERO),
            ApuestasUiState("3", Color.Red, TipoApuestaEnum.NUMERO),
            ApuestasUiState("26", Color.Black, TipoApuestaEnum.NUMERO)
        )
    }
    val degreesPerItems = remember { 360f / items.size.toFloat() }

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
            Text(
                text = tiempo.toString(),
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Black, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, Color(0xFFD4AF37), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Box {
                items.forEachIndexed { index, item ->
                    RuletaCompleta(
                        modifier = Modifier.rotate(degrees = degreesPerItems * index),
                        size = 350.dp,
                        brush = SolidColor(item.color),
                        degree = degreesPerItems,
                        dibujarHub = index == items.size - 1
                    ) {
                        Text(
                            text = item.valor,
                            color = if (item.color == Color.Black) Color.White else Color.Black
                        )
                    }
                }
            }

            // Tablero de los números y apuestas especiales
            TableroRuleta(
                enabled = enabled,
                listaApuestaMarcado = listaApuestaMarcado,
                listaApuestaDefinitiva = listaApuestaDefinitiva,
                esRojo = listaNumerosRojos,
                bloques = listaNumeros,
                onRuletaEvent = onRuletaEvent,
            )


            // Parte del usuario para apostar
            VistaJugadorRuleta(
                onRuletaEvent = onRuletaEvent,
                apuestaUsuario = apuestaUsuario,
                saldo = usuarioUiState.saldo,
                onAumentarSaldoUsuario = onAumentarSaldoUsuario,
                onBajarSaldoUsuario = onBajarSaldoUsuario,
                enabled = enabled
            )
        }
    }
}
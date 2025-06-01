package com.pmdm.casino.ui.features

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastSumBy
import com.pmdm.casino.ui.features.blackJack.CartaUiState
import com.pmdm.casino.ui.features.ruleta.ApuestasUiState
import com.pmdm.casino.ui.features.ruleta.TipoApuestaEnum
import com.pmdm.casino.ui.views.MainActivity
import java.math.BigDecimal

// TODOS VM
fun reiniciarApp(context: Context): Boolean {
    context.startActivity(Intent(context, MainActivity::class.java))
    (context as Activity).finish()

    return false
}

// BLACK JACK
// Función que convierte una carta en su valor correspondiente
private fun getValorCarta(carta: CartaUiState, puntosTotalesUsuario: Int): Int {
    return when (carta.valor) {
        "J", "Q", "K" -> 10
        "A" -> if (puntosTotalesUsuario + 11 > 21) 1 else 11
        else -> carta.valor.toInt()
    }
}

fun sumarPuntos(
    puntosTotalesUsuario: Int,
    cartasUiState: List<CartaUiState>,
    cartaRecienteUiState: CartaUiState? = null
): Pair<Int, Boolean> {
    var puntosTotalesUsuarioMetodo = cartasUiState.fastSumBy { suma ->
        getValorCarta(suma, puntosTotalesUsuario)
    }

    puntosTotalesUsuarioMetodo += cartaRecienteUiState?.let {
        getValorCarta(
            it,
            puntosTotalesUsuario
        )
    } ?: 0

    if (puntosTotalesUsuarioMetodo >= 21) {
        return Pair(puntosTotalesUsuarioMetodo, true)
    }

    return Pair(puntosTotalesUsuarioMetodo, false)
}

fun evaluarResultado(puntosUsuario: Int, puntosMaquina: Int): String {
    return when {
        puntosUsuario == 21 -> "Ganado"
        puntosUsuario == puntosMaquina -> "Empate"
        puntosUsuario > 21 || (puntosMaquina in puntosUsuario..21) -> "Perdido"
        else -> "Ganado"
    }
}

// Ruleta
fun pagarApuesta(
    numeroGanador: Int,
    apuestaUsuario: Map<ApuestasUiState, BigDecimal>,
    listaNumeros: List<List<List<ApuestasUiState>>>,
    listaNumerosRojos: Set<Int>
): BigDecimal {
    var paga: BigDecimal = 0.toBigDecimal()
    val numeroGanadorString = numeroGanador.toString()

    apuestaUsuario.forEach {
        val tipo = it.key.tipoApuesta
        val valor = it.key.valor
        val apuesta = it.value

        paga += when {
            // APUESTAS NUMEROS
            tipo == TipoApuestaEnum.NUMERO && numeroGanadorString == valor -> apuesta * 35.toBigDecimal()

            // APUESTA COLOR
            (tipo == TipoApuestaEnum.NEGRO && !listaNumerosRojos.contains(
                numeroGanador
            ))
                    || (tipo == TipoApuestaEnum.ROJO && listaNumerosRojos.contains(
                numeroGanador
            )) -> apuesta * 2.toBigDecimal()

            // APUESTA PAR/IMPAR
            (tipo == TipoApuestaEnum.PAR && numeroGanador != 0 && (numeroGanador % 2 == 0))
                    || (tipo == TipoApuestaEnum.IMPAR && (numeroGanador % 2 != 0)) -> apuesta * 2.toBigDecimal()

            // APUESTA MITADES
            (tipo == TipoApuestaEnum.MITAD1 && numeroGanador > 0 && numeroGanador <= 18)
                    || (tipo == TipoApuestaEnum.MITAD2 && numeroGanador > 18 && numeroGanador <= 36) -> apuesta * 2.toBigDecimal()

            // APUESTA DOCENAS
            (tipo == TipoApuestaEnum.DOCENA1 && numeroGanador > 0 && numeroGanador <= 12)
                    || (tipo == TipoApuestaEnum.DOCENA2 && numeroGanador > 12 && numeroGanador <= 24)
                    || (tipo == TipoApuestaEnum.DOCENA3 && numeroGanador > 24 && numeroGanador <= 36) -> apuesta * 4.toBigDecimal()

            // APUESTA COLUMNA
            (tipo == TipoApuestaEnum.COLUMNA1 &&
                    listaNumeros.fastAny { listaIntermedia ->
                        listaIntermedia.fastAny { listaProfunda ->
                            listaProfunda[0].valor == numeroGanadorString
                        }
                    })
                    || (tipo == TipoApuestaEnum.COLUMNA2 && listaNumeros.fastAny { listaIntermedia ->
                listaIntermedia.fastAny { listaProfunda ->
                    listaProfunda[1].valor == numeroGanadorString
                }
            })
                    || (tipo == TipoApuestaEnum.COLUMNA3 && listaNumeros.fastAny { listaIntermedia ->
                listaIntermedia.fastAny { listaProfunda ->
                    listaProfunda[2].valor == numeroGanadorString
                }
            }) -> apuesta * 4.toBigDecimal()

            else -> 0.toBigDecimal()
        }
    }

    return paga
}
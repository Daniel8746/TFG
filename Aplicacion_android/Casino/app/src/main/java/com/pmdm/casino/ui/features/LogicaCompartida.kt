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
    numeroSalido: String,
    apuestaUsuario: Map<ApuestasUiState, BigDecimal>,
    listaNumeros: List<List<List<ApuestasUiState>>>,
    listaNumerosRojos: Set<Int>
): BigDecimal {
    var paga: BigDecimal = 0.toBigDecimal()

    apuestaUsuario.forEach {
        paga += when {
            // APUESTAS NUMEROS
            it.key.tipoApuesta == TipoApuestaEnum.NUMERO && numeroSalido == it.key.valor -> it.value * 35.toBigDecimal()

            // APUESTA COLOR
            (it.key.tipoApuesta == TipoApuestaEnum.NEGRO && listaNumerosRojos.contains(numeroSalido.toInt()))
                    || (it.key.tipoApuesta == TipoApuestaEnum.ROJO && listaNumerosRojos.contains(
                numeroSalido.toInt()
            )) -> it.value * 2.toBigDecimal()

            // APUESTA PAR/IMPAR
            (it.key.tipoApuesta == TipoApuestaEnum.PAR && numeroSalido.toInt() != 0 && (numeroSalido.toInt() % 2 == 0))
                    || (it.key.tipoApuesta == TipoApuestaEnum.IMPAR && (numeroSalido.toInt() % 2 != 0)) -> it.value * 2.toBigDecimal()

            // APUESTA MITADES
            (it.key.tipoApuesta == TipoApuestaEnum.MITAD1 && numeroSalido.toInt() > 0 && numeroSalido.toInt() <= 18)
                    || (it.key.tipoApuesta == TipoApuestaEnum.MITAD2 && numeroSalido.toInt() > 18 && numeroSalido.toInt() <= 36) -> it.value * 2.toBigDecimal()

            // APUESTA DOCENAS
            (it.key.tipoApuesta == TipoApuestaEnum.DOCENA1 && numeroSalido.toInt() > 0 && numeroSalido.toInt() <= 12)
                    || (it.key.tipoApuesta == TipoApuestaEnum.DOCENA2 && numeroSalido.toInt() > 12 && numeroSalido.toInt() <= 24)
                    || (it.key.tipoApuesta == TipoApuestaEnum.DOCENA3 && numeroSalido.toInt() > 24 && numeroSalido.toInt() <= 36) -> it.value * 4.toBigDecimal()

            // APUESTA COLUMNA
            (it.key.tipoApuesta == TipoApuestaEnum.COLUMNA1 &&
                    listaNumeros.fastAny { listaIntermedia ->
                        listaIntermedia.fastAny { listaProfunda ->
                            listaProfunda[0].valor == numeroSalido
                        }
                    })
                    || (it.key.tipoApuesta == TipoApuestaEnum.COLUMNA2 && listaNumeros.fastAny { listaIntermedia ->
                listaIntermedia.fastAny { listaProfunda ->
                    listaProfunda[1].valor == numeroSalido
                }
            })
                    || (it.key.tipoApuesta == TipoApuestaEnum.COLUMNA3 && listaNumeros.fastAny { listaIntermedia ->
                listaIntermedia.fastAny { listaProfunda ->
                    listaProfunda[2].valor == numeroSalido
                }
            }) -> it.value * 4.toBigDecimal()

            else -> 0.toBigDecimal()
        }
    }

    return paga
}
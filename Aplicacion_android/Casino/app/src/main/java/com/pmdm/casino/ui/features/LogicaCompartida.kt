package com.pmdm.casino.ui.features

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.ui.util.fastSumBy
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.views.MainActivity

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
package com.pmdm.casino.ui.features.casino

import com.pmdm.casino.R

enum class JuegosCasinoEnum(val clave: String, val imagen: Int) {
    BLACKJACK(clave = "Blackjack", imagen = R.drawable.imagenblackjack),
    RULETA(clave = "Ruleta", imagen = R.drawable.imagenruleta),
    TRAGAMONEDAS(clave = "Traga monedas", imagen = R.drawable.imagentragamonedas)
}

fun String.toJuegoEnum(): JuegosCasinoEnum? {
    return JuegosCasinoEnum.entries.find {
        this.startsWith(
            prefix = it.clave, ignoreCase = true
        )
    }
}
package com.pmdm.casino.ui.features.usuarioCasino

import java.math.BigDecimal

data class UsuarioCasinoUiState(
    val correo: String = "",
    val saldo: BigDecimal = 0.toBigDecimal()
)
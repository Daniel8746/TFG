package com.pmdm.casino.ui.features

import java.math.BigDecimal

data class UsuarioCasinoUiState(
    val correo: String = "",
    val saldo: BigDecimal = BigDecimal(0)
)
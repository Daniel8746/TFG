package com.pmdm.casino.ui.features.casino

import java.math.BigDecimal

data class CasinoUiState(
    val nombre: String = "",
    val tipo: String = "",
    val reglas: String = ""
)

data class UsuarioCasinoUiState(
    val correo: String = "",
    val saldo: BigDecimal = BigDecimal(0)
)
package com.pmdm.casino.ui.features.usuarioCasino

import java.math.BigDecimal

sealed interface UsuarioCasinoEvent {
    data class AumentarSaldo(val saldo: BigDecimal) : UsuarioCasinoEvent
    data class BajarSaldoBlackJack(val saldo: BigDecimal) : UsuarioCasinoEvent
}
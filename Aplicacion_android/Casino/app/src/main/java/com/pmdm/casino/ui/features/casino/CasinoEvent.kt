package com.pmdm.casino.ui.features.casino

import java.math.BigDecimal

sealed interface CasinoEvent {
    data class OnBlackJack(val onNavigateBlackJack:((correo: String, saldo: BigDecimal)->Unit)): CasinoEvent
    data class OnRuleta(val onNavigateRuleta:((correo: String, saldo: BigDecimal)->Unit)): CasinoEvent
    data class OnTragaMonedas(val onNavigateTragaMonedas:((correo: String, saldo: BigDecimal)->Unit)): CasinoEvent
}
package com.pmdm.casino.ui.features.casino

sealed interface JuegosEvent {
    data class OnBlackJack(val onNavigateBlackJack: () -> Unit) :
        JuegosEvent

    data class OnRuleta(val onNavigateRuleta: () -> Unit) :
        JuegosEvent

    data class OnTragaMonedas(val onNavigateTragaMonedas: () -> Unit) :
        JuegosEvent
}
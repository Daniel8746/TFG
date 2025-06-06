package com.pmdm.casino.ui.features.juegos

sealed interface JuegosEvent {
    data class OnBlackJack(val onNavigateBlackJack: () -> Unit) :
        JuegosEvent

    data class OnRuleta(val onNavigateRuleta: () -> Unit) :
        JuegosEvent

    data class OnTragaMonedas(val onNavigateTragaMonedas: () -> Unit) :
        JuegosEvent
}
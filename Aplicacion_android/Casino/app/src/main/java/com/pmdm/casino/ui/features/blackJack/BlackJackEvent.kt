package com.pmdm.casino.ui.features.blackJack

sealed interface BlackJackEvent {
    data object OnPlantarse: BlackJackEvent
    data object OnPedirCarta: BlackJackEvent
}
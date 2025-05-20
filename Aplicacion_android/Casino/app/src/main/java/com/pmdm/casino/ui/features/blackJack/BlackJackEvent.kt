package com.pmdm.casino.ui.features.blackJack

import java.math.BigDecimal

sealed interface BlackJackEvent {
    data object OnPlantarse : BlackJackEvent
    data object OnPedirCarta : BlackJackEvent
    data class OnValueApuestaUsuarioChanged(val apuesta: BigDecimal) : BlackJackEvent
}
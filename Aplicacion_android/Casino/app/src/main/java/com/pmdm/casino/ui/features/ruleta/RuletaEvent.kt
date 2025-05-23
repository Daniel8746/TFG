package com.pmdm.casino.ui.features.ruleta

import java.math.BigDecimal

sealed interface RuletaEvent {
    data class ApuestaChanged(val apuesta: BigDecimal) : RuletaEvent
    data object FlechaSubirPulsado : RuletaEvent
    data object FlechaBajarPulsado : RuletaEvent
    data class ApuestaSeleccionada(val apuestasUiState: ApuestasUiState) : RuletaEvent
    data class Apostar(val apuestaUsuario: BigDecimal) : RuletaEvent
    data object QuitarApuesta : RuletaEvent
    data object FinalizarJuego : RuletaEvent
}
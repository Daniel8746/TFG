package com.pmdm.casino.ui.features.ruleta

sealed interface RuletaEvent {
    data class ApuestaChanged(val apuesta: Int) : RuletaEvent
    data object FlechaSubirPulsado : RuletaEvent
    data object FlechaBajarPulsado : RuletaEvent
    data class ApuestaSeleccionada(val tipoApuestaEnum: TipoApuestaEnum, val numero: String) :
        RuletaEvent

    data object Apostar : RuletaEvent
    data object QuitarApuesta : RuletaEvent
    data object EntrarJuego : RuletaEvent
    data object FinalizarJuego : RuletaEvent
}
package com.pmdm.casino.ui.features.blackJack

import kotlinx.serialization.Serializable

@Serializable
data class DetallesBlackJack(
    val puntosUsuario: Int = 0,
    val puntosCrupier: Int = 0,
    val cartasUsuario: List<CartaUiState> = listOf(),
    val cartasCrupier: List<CartaUiState> = listOf()
)
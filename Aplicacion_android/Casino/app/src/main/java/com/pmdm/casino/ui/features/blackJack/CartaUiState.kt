package com.pmdm.casino.ui.features.blackJack

import kotlinx.serialization.Serializable

@Serializable
data class CartaUiState(
    val palo: String,
    val valor: String,
)
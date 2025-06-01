package com.pmdm.casino.ui.features.ruleta

import androidx.compose.ui.graphics.Color
import com.pmdm.casino.data.serialization.ColorSerializer
import kotlinx.serialization.Serializable

@Serializable
data class DetallesRuleta(
    val apuestaUsuario: Set<ApuestasUiState>
)

@Serializable
data class DetallesRuletaFinalizar(
    val apuestaUsuario: Set<ApuestasUiState>,
    val numeroGanador: Int,
    @Serializable(with = ColorSerializer::class) val colorNumeroGanador: Color
)
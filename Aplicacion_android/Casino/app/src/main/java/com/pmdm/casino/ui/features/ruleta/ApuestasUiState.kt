package com.pmdm.casino.ui.features.ruleta

import androidx.compose.ui.graphics.Color
import com.pmdm.casino.data.serialization.ColorSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ApuestasUiState(
    val valor: String,
    @Serializable(with = ColorSerializer::class) val color: Color,
    val tipoApuesta: TipoApuestaEnum
)
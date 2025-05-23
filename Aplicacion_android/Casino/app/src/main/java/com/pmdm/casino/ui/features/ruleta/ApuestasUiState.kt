package com.pmdm.casino.ui.features.ruleta

import androidx.compose.ui.graphics.Color

data class ApuestasUiState(
    val valor: String,
    val color: Color,
    val tipoApuesta: TipoApuestaEnum
)
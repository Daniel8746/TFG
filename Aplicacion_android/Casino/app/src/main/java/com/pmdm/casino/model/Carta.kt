package com.pmdm.casino.model

data class Carta(
    val palo: Palo,
    val valor: String,
    val color: Color
)

enum class Palo {
    CORAZONES,
    DIAMANTES,
    TREBOLES,
    PICAS
}

enum class Color {
    BLANCO,
    NEGRO
}
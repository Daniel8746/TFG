package com.pmdm.casino.model

import java.math.BigDecimal

data class Apuesta(
    val correoUsuario: String,
    val nombreJuego: String,
    val montoApostado: BigDecimal,
    val resultado: String,
    val detallesResultado: String
)
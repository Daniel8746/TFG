package com.pmdm.casino.data.services.apuestas

import java.math.BigDecimal

data class ApuestaApiRecord(
    val correoUsuario: String,
    val nombreJuego: String,
    val montoApostado: BigDecimal,
    val resultado: String,
    val detallesResultado: String
)
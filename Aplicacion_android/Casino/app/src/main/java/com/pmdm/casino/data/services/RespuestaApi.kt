package com.pmdm.casino.data.services

import java.math.BigDecimal

data class RespuestaApi(
    val saldo: BigDecimal = BigDecimal(0),
    val token: String = ""
)
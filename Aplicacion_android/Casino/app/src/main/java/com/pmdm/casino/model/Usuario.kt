package com.pmdm.casino.model

import java.math.BigDecimal

data class Usuario(
    val nombre: String = "",
    val apellidos: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val telefono: String = "",
    val saldo: BigDecimal = 0.toBigDecimal(),
    val recordarCuenta: Boolean = false
)
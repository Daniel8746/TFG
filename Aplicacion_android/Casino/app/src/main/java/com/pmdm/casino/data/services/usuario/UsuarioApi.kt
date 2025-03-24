package com.pmdm.casino.data.services.usuario

import java.math.BigDecimal

data class UsuarioApi(
    val nombre : String,
    val apellidos : String,
    val correo : String,
    val contrasena: String,
    val telefono: String,
    val saldo: BigDecimal,
    val recordarCuenta: Boolean
)

data class UsuarioApiRecord(
    val correo: String,
    val contrasena: String,
    val saldo: BigDecimal
)
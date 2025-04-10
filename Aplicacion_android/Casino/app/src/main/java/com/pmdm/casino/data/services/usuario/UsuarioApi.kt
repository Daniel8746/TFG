package com.pmdm.casino.data.services.usuario

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class UsuarioApi(
    val nombre : String,
    val apellidos : String,
    val correo : String,
    @SerializedName("contrasenya")
    val contrasena: String,
    val telefono: String,
    val saldo: BigDecimal,
    val recordarCuenta: Boolean
)

data class UsuarioApiRecord(
    val correo: String,
    @SerializedName("contrasenya")
    val contrasena: String,
    val saldo: BigDecimal
)
package com.pmdm.casino.data.services.usuario

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/*
* CLASE PARA NUEVO USUARIO
 */
data class NuevoUsuarioApi(
    val nombre: String,
    val apellidos: String,
    val correo: String,
    @SerializedName("contrasenya")
    val contrasena: String,
    val telefono: String,
    val saldo: BigDecimal,
    val recordarCuenta: Boolean
)

/*
* CLASE PARA LOGUEARSE
 */
data class UsuarioApiRecord(
    val correo: String,
    @SerializedName("contrasenya")
    val contrasena: String
)

/*
* CLASE PARA LA RESPUESTA DE LOGIN
*/
data class UsuarioRespuestaApi(
    val saldo: BigDecimal = 0.toBigDecimal(),
    val token: String = ""
)
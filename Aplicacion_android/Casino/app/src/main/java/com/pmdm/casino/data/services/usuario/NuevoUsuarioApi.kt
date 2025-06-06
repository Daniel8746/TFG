package com.pmdm.casino.data.services.usuario

import java.math.BigDecimal

/*
* CLASE PARA NUEVO USUARIO
 */
data class NuevoUsuarioApi(
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val contrasenya: String,
    val telefono: String,
    val saldo: BigDecimal,
    val recordarCuenta: Boolean
)

/*
* CLASE PARA LOGUEARSE
 */
data class UsuarioApiRecord(
    val correo: String,
    val contrasenya: String = "",
    val saldo: BigDecimal = 0.toBigDecimal()
)

/*
* CLASE PARA LA RESPUESTA DE LOGIN
*/
data class UsuarioRespuestaApi(
    val saldo: BigDecimal = 0.toBigDecimal(),
    val token: String = ""
)

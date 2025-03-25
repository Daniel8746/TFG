package com.pmdm.casino.data

import com.pmdm.casino.data.services.juegos.JuegosApi
import com.pmdm.casino.data.services.usuario.UsuarioApi
import com.pmdm.casino.data.services.usuario.UsuarioApiRecord
import com.pmdm.casino.model.Juegos
import com.pmdm.casino.model.Usuario

// Usuario
fun Usuario.toUsuarioApi(): UsuarioApi = UsuarioApi(
    nombre, apellidos, correo, contrasena, telefono, saldo, recordarCuenta
)

fun Usuario.toUsuarioApiRecord(): UsuarioApiRecord = UsuarioApiRecord(
    correo, contrasena, saldo
)

// Juegos
fun JuegosApi.toJuego(): Juegos = Juegos(
    nombre, tipo, reglas
)

fun List<JuegosApi>.toJuegos(): List<Juegos> =
    map { it.toJuego() }


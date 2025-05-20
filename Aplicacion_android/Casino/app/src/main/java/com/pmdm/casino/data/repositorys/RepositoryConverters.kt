package com.pmdm.casino.data.repositorys

import com.pmdm.casino.data.services.apuestas.ApuestaApiRecord
import com.pmdm.casino.data.services.blackJack.CartaApi
import com.pmdm.casino.data.services.juegos.JuegosApi
import com.pmdm.casino.data.services.usuario.NuevoUsuarioApi
import com.pmdm.casino.data.services.usuario.UsuarioApiRecord
import com.pmdm.casino.model.Apuesta
import com.pmdm.casino.model.Carta
import com.pmdm.casino.model.Juegos
import com.pmdm.casino.model.Usuario

// Usuario
fun Usuario.toUsuarioApi(): NuevoUsuarioApi = NuevoUsuarioApi(
    nombre, apellidos, correo, contrasena, telefono, saldo, recordarCuenta
)

fun Usuario.toUsuarioApiRecord(): UsuarioApiRecord = UsuarioApiRecord(
    correo, contrasena
)

// Juegos
fun JuegosApi.toJuego(): Juegos = Juegos(
    nombre, tipo, reglas
)

fun List<JuegosApi>.toJuegos(): List<Juegos> =
    map { it.toJuego() }

// BlackJack

fun CartaApi.toCarta(): Carta = Carta(
    palo, valor
)

fun List<CartaApi>.toCartas(): List<Carta> =
    map { it.toCarta() }

// Apuestas
fun Apuesta.toApuestaApi(): ApuestaApiRecord = ApuestaApiRecord(
    correoUsuario = correoUsuario,
    nombreJuego = nombreJuego,
    saldoApostado = montoApostado,
    resultado = resultado
)


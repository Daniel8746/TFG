package com.pmdm.casino.ui.features

import com.pmdm.casino.model.Juegos
import com.pmdm.casino.model.Usuario
import com.pmdm.casino.ui.features.casino.JuegosUiState
import com.pmdm.casino.ui.features.login.LoginUiState
import com.pmdm.casino.ui.features.nuevousuario.NuevoUsuarioUiState

// Login
fun LoginUiState.toUsuario(): Usuario = Usuario(
    correo = login, contrasena = password, recordarCuenta = recordarCuenta
)

// Nuevo Usuario
fun NuevoUsuarioUiState.toUsuario(): Usuario = Usuario(
    nombre, apellidos, correo, password, telefono.toString()
)

// Casino
fun Juegos.toJuegosUiState(): JuegosUiState = JuegosUiState(
    nombre, tipo, reglas
)

fun List<Juegos>.toJuegosUiStates(): List<JuegosUiState> =
    map { it.toJuegosUiState() }

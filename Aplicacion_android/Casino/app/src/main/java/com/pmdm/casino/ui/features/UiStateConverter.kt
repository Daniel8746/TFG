package com.pmdm.casino.ui.features

import com.pmdm.casino.model.Usuario
import com.pmdm.casino.ui.features.login.LoginUiState
import com.pmdm.casino.ui.features.nuevousuario.NuevoUsuarioUiState

fun LoginUiState.toUsuario(): Usuario = Usuario(
    correo = login, contrasena = password, recordarCuenta = recordarCuenta
)

fun NuevoUsuarioUiState.toUsuario(): Usuario = Usuario(
    nombre, apellidos, correo, password, telefono.toString()
)
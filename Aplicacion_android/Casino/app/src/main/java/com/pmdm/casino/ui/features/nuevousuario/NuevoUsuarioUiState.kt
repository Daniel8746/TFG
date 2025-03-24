package com.pmdm.casino.ui.features.nuevousuario

data class NuevoUsuarioUiState(
    val id : Int = 0,
    val nombre : String = "",
    val apellidos : String = "",
    val correo : String = "",
    val password: String = "",
    val telefono: String = ""
)
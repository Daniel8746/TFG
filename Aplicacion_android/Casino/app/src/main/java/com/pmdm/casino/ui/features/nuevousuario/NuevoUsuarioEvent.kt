package com.pmdm.casino.ui.features.nuevousuario

sealed interface NuevoUsuarioEvent {
    data class NombreChanged(val nombre: String) : NuevoUsuarioEvent
    data class ApellidosChanged(val apellidos: String) : NuevoUsuarioEvent
    data class CorreoChanged(val correo: String) : NuevoUsuarioEvent
    data class PasswordChanged(val password: String) : NuevoUsuarioEvent
    data class TelefonoChanged(val telefono: String) : NuevoUsuarioEvent
    data class  OnClickNuevoUsuario(val onNavigateLogin:(()->Unit)?): NuevoUsuarioEvent
}
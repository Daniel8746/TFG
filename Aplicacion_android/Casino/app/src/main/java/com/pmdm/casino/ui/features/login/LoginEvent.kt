package com.pmdm.casino.ui.features.login

sealed interface LoginEvent {
    data class LoginChanged(val login: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data class  OnClickLogearse(val onNavigateJuego:((correo:String)->Unit)?): LoginEvent
    data class OnClickNuevoUsuario(val onNavigateNuevoUsuario: () -> Unit): LoginEvent
}

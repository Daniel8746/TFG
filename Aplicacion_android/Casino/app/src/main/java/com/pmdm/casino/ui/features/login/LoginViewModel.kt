package com.pmdm.casino.ui.features.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.UsuarioRepository
import com.pmdm.casino.ui.features.toUsuario

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorLogin
) : ViewModel() {
    private val _usuarioLogin = MutableStateFlow(false)  // Estado inicial false
    val usuarioLogin: StateFlow<Boolean> = _usuarioLogin.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var usuarioUiState by mutableStateOf(LoginUiState())
        private set
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set

    private val snackbarHostState by mutableStateOf(SnackbarHostState())

    fun onLoginEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.LoginChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    login = loginEvent.login
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionLogin = validadorLogin.validadorLogin.valida(loginEvent.login)
                )
            }

            is LoginEvent.PasswordChanged -> {
                usuarioUiState = usuarioUiState.copy(
                    password = loginEvent.password
                )
                validacionLoginUiState = validacionLoginUiState.copy(
                    validacionPassword = validadorLogin.validadorPassword.valida(loginEvent.password)
                )
            }

            is LoginEvent.OnClickLogearse -> {
                viewModelScope.launch {
                    validacionLoginUiState = validadorLogin.valida(usuarioUiState)
                    if (!validacionLoginUiState.hayError) {
                        logearse()

                        if (_usuarioLogin.value == true) {
                            delay(1000)
                            loginEvent.onNavigateJuego?.let { it(usuarioUiState.login) }
                            mostrarSnackBar(
                                snackbarHostState,
                                "Iniciando la sesión con el usuario ${usuarioUiState.login}"
                            )
                        }
                    }
                }

            }

            is LoginEvent.OnClickNuevoUsuario -> {
                viewModelScope.launch {
                    loginEvent.onNavigateNuevoUsuario
                }
            }
        }
    }

    private suspend fun logearse() {
        _isLoading.value = true

        usuarioRepository.login(usuarioUiState.toUsuario()).collect{
            _usuarioLogin.value = it
            _isLoading.value = false
        }
    }

    private suspend fun mostrarSnackBar(snackbarHostState: SnackbarHostState, mensaje: String) {
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(
            message = mensaje,
        )
    }
}
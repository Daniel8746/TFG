package com.pmdm.casino.ui.features.login

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.UsuarioRepository
import com.pmdm.casino.model.TokenManager
import com.pmdm.casino.ui.features.toUsuario
import dagger.hilt.android.internal.Contexts.getApplication

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorLogin,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _usuarioLogin = MutableStateFlow(true)  // Estado inicial true
    val usuarioLogin: StateFlow<Boolean> = _usuarioLogin.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var usuarioUiState by mutableStateOf(LoginUiState())
        private set
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set

    private val snackbarHostState by mutableStateOf(SnackbarHostState())

    private var _saldo = MutableStateFlow(BigDecimal(0))
    private var _token = MutableStateFlow("")

    var recordarmeState by mutableStateOf(false)

    fun onRecordarmeState(recordarme: Boolean) {
        recordarmeState = recordarme;
    }

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

                        if (_usuarioLogin.value) {
                            if (_token.value.isNotEmpty()) {
                                if (recordarmeState) {
                                    TokenManager.saveToken(
                                        getApplication(context),
                                        _token.value
                                    )
                                }

                                delay(1000)
                                loginEvent.onNavigateJuego?.let { it(usuarioUiState.login, _saldo.value) }
                                mostrarSnackBar(
                                    snackbarHostState,
                                    "Iniciando la sesión con el usuario ${usuarioUiState.login}"
                                )
                            }
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
            delay(2500)
            _usuarioLogin.value = it.first
            _saldo.value = it.second
            _token.value = it.third

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
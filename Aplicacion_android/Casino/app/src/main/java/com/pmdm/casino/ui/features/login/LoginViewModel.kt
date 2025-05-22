package com.pmdm.casino.ui.features.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.local.TokenManager
import com.pmdm.casino.data.repositorys.UsuarioRepository
import com.pmdm.casino.ui.features.reiniciarApp
import com.pmdm.casino.ui.features.toUsuario
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorLogin: ValidadorLogin,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _usuarioCorrecto = MutableStateFlow(true)  // Estado inicial true
    val usuarioCorrecto: StateFlow<Boolean> = _usuarioCorrecto.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var usuarioUiState by mutableStateOf(LoginUiState())
        private set
    var validacionLoginUiState by mutableStateOf(ValidacionLoginUiState())
        private set

    private var _saldo = MutableStateFlow(0.toBigDecimal())
    val saldo: StateFlow<BigDecimal> = _saldo.asStateFlow()

    private var _token = MutableStateFlow("")

    var recordarmeState by mutableStateOf(false)

    var errorApi by mutableStateOf(false)

    fun onRecordarmeState(recordarme: Boolean) {
        recordarmeState = recordarme
    }

    var reintentarConexion by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
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

                        if (_usuarioCorrecto.value) {
                            if (_token.value.isNotEmpty()) {
                                if (recordarmeState) {
                                    TokenManager.saveToken(
                                        getApplication(context),
                                        _token.value
                                    )
                                }

                                loginEvent.onNavigateJuego()
                            }
                        }
                    }
                }
            }

            is LoginEvent.OnClickNuevoUsuario -> {
                loginEvent.onNavigateNuevoUsuario
            }
        }
    }

    private suspend fun logearse() {
        _isLoading.value = true

        usuarioRepository.login(usuarioUiState.toUsuario())
            .catch { e ->
                when (e) {
                    is NoNetworkException -> {
                        Log.e("NoNetworkException", "Error: ${e.localizedMessage}")
                        reintentarConexion = true
                    }

                    is SocketTimeoutException -> {
                        Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
                        errorApi = true
                    }

                    is ConnectException -> {
                        Log.e("Connect fail", "Error: ${e.localizedMessage}")
                        errorApi = true
                    }
                }
            }.collect {
                _usuarioCorrecto.value = it.first
                _saldo.value = it.second
                _token.value = it.third
                delay(2500)
                _isLoading.value = false
            }
    }
}
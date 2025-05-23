package com.pmdm.casino.ui.features.nuevousuario

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.UsuarioRepository
import com.pmdm.casino.ui.features.reiniciarApp
import com.pmdm.casino.ui.features.toUsuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class NuevoUsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorNuevoUsuario: ValidadorNuevoUsuario
) : ViewModel() {
    private val _usuarioCreado = MutableStateFlow(true)  // Estado inicial true
    val usuarioCreado: StateFlow<Boolean> = _usuarioCreado.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var nuevoUsuarioUiState by mutableStateOf(NuevoUsuarioUiState())
    var validacionNuevoUsuarioUiState by mutableStateOf(ValidacionNuevoUsuarioUiState())

    var reintentarConexion by mutableStateOf(false)

    var errorApi by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    fun onNuevoUsuarioEvent(nuevoUsuarioEvent: NuevoUsuarioEvent) {
        when (nuevoUsuarioEvent) {
            is NuevoUsuarioEvent.ApellidosChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    apellidos = nuevoUsuarioEvent.apellidos
                )

                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionApellidos = validadorNuevoUsuario.validadorApellidos.valida(
                        nuevoUsuarioEvent.apellidos
                    )
                )
            }

            is NuevoUsuarioEvent.CorreoChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    correo = nuevoUsuarioEvent.correo
                )

                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionCorreo = validadorNuevoUsuario.validadorCorreo.valida(
                        nuevoUsuarioEvent.correo
                    )
                )
            }

            is NuevoUsuarioEvent.NombreChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    nombre = nuevoUsuarioEvent.nombre
                )

                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionNombre = validadorNuevoUsuario.validadorNombre.valida(
                        nuevoUsuarioEvent.nombre
                    )
                )
            }

            is NuevoUsuarioEvent.OnClickNuevoUsuario -> {
                viewModelScope.launch {
                    validacionNuevoUsuarioUiState =
                        validadorNuevoUsuario.valida(nuevoUsuarioUiState)

                    if (!validacionNuevoUsuarioUiState.hayError) {
                        crearCuenta()
                        if (_usuarioCreado.value) {
                            nuevoUsuarioEvent.onNavigateLogin?.let { it() }
                        }
                    }
                }
            }

            is NuevoUsuarioEvent.PasswordChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    password = nuevoUsuarioEvent.password
                )
                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionPassword = validadorNuevoUsuario.validadorPassword.valida(
                        nuevoUsuarioEvent.password
                    )
                )
            }

            is NuevoUsuarioEvent.TelefonoChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    telefono = nuevoUsuarioEvent.telefono
                )
                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionTelefono = validadorNuevoUsuario.validadorTelefono.valida(
                        nuevoUsuarioEvent.telefono
                    )
                )
            }
        }
    }

    private suspend fun crearCuenta() {
        _isLoading.value = true
        usuarioRepository.crear(nuevoUsuarioUiState.toUsuario())
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
                _usuarioCreado.value = it
                delay(2500)
                _isLoading.value = false
            }
    }
}
package com.pmdm.casino.ui.features.nuevousuario

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
class NuevoUsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val validadorNuevoUsuario: ValidadorNuevoUsuario
): ViewModel() {
    private val _usuarioCreado = MutableStateFlow(false)  // Estado inicial false
    val usuarioCreado: StateFlow<Boolean> = _usuarioCreado.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var nuevoUsuarioUiState by mutableStateOf(NuevoUsuarioUiState())
    var validacionNuevoUsuarioUiState by mutableStateOf(ValidacionNuevoUsuarioUiState())

    private val snackbarHostState by mutableStateOf(SnackbarHostState())

    init {
        // Si no valido al principio no sé porque peta la aplicación
        validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
            validacionTelefono = validadorNuevoUsuario.validadorTelefono.valida("")
        )
    }

    fun onNuevoUsuarioEvent(nuevoUsuarioEvent: NuevoUsuarioEvent) {
        when(nuevoUsuarioEvent) {
            is NuevoUsuarioEvent.ApellidosChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    apellidos = nuevoUsuarioEvent.apellidos
                )

                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionApellidos = validadorNuevoUsuario.validadorApellidos.valida(nuevoUsuarioEvent.apellidos)
                )
            }

            is NuevoUsuarioEvent.CorreoChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    correo = nuevoUsuarioEvent.correo
                )

                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionCorreo = validadorNuevoUsuario.validadorCorreo.valida(nuevoUsuarioEvent.correo)
                )
            }
            is NuevoUsuarioEvent.NombreChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    nombre = nuevoUsuarioEvent.nombre
                )

                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionNombre = validadorNuevoUsuario.validadorNombre.valida(nuevoUsuarioEvent.nombre)
                )
            }
            is NuevoUsuarioEvent.OnClickNuevoUsuario -> {
                viewModelScope.launch {
                    validacionNuevoUsuarioUiState = validadorNuevoUsuario.valida(nuevoUsuarioUiState)

                    if (!validacionNuevoUsuarioUiState.hayError) {
                        crearCuenta()
                        if (_usuarioCreado.value) {
                            delay(1000)
                            nuevoUsuarioEvent.onNavigateLogin
                            mostrarSnackBar(
                                snackbarHostState,
                                "Creado la sesión con el usuario ${nuevoUsuarioUiState.correo}"
                            )
                        }
                    }
                }


            }
            is NuevoUsuarioEvent.PasswordChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    password = nuevoUsuarioEvent.password
                )
                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionPassword = validadorNuevoUsuario.validadorPassword.valida(nuevoUsuarioEvent.password)
                )
            }
            is NuevoUsuarioEvent.TelefonoChanged -> {
                nuevoUsuarioUiState = nuevoUsuarioUiState.copy(
                    telefono = nuevoUsuarioEvent.telefono
                )
                validacionNuevoUsuarioUiState = validacionNuevoUsuarioUiState.copy(
                    validacionTelefono = validadorNuevoUsuario.validadorTelefono.valida(nuevoUsuarioEvent.telefono)
                )
            }
        }
    }

    private suspend fun mostrarSnackBar(snackbarHostState: SnackbarHostState, mensaje: String) {
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(
            message = mensaje,
        )
    }

    private suspend fun crearCuenta() {
        _isLoading.value = true
        usuarioRepository.crear(nuevoUsuarioUiState.toUsuario()).collect {
            _usuarioCreado.value = it
            _isLoading.value = false
        }
    }
}
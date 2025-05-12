package com.pmdm.casino.ui.features.casino

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.JuegosRepository
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.reiniciarApp
import com.pmdm.casino.ui.features.toJuegosUiStates
import com.pmdm.casino.ui.navigation.CasinoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class JuegosViewModel @Inject constructor(
    private val casinoRepository: JuegosRepository
) : ViewModel() {
    var usuarioUiState by mutableStateOf(UsuarioCasinoUiState())
    private val _juegosUiState = MutableStateFlow<List<JuegosUiState>>(emptyList())
    val juegosUiState: StateFlow<List<JuegosUiState>> = _juegosUiState.asStateFlow()

    var isAyudaAbierta by mutableStateOf(false)

    var reintentarConexion by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    init {
        try {
            viewModelScope.launch {
                casinoRepository.getJuegos().collect {
                    _juegosUiState.value = it?.toJuegosUiStates() ?: emptyList()
                }
            }
        } catch (e: NoNetworkException) {
            Log.e("NoNetworkException", "Error: ${e.localizedMessage}")
            reintentarConexion = true
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
        } catch (e: ConnectException) {
            Log.e("Connect fail", "Error: ${e.localizedMessage}")
        }
    }

    fun onCasinoEvent(casinoEvent: JuegosEvent) {
        try {
            when (casinoEvent) {
                is JuegosEvent.OnBlackJack -> {
                    casinoEvent.onNavigateBlackJack(usuarioUiState.correo, usuarioUiState.saldo)
                }

                is JuegosEvent.OnRuleta -> {
                    casinoEvent.onNavigateRuleta(usuarioUiState.correo, usuarioUiState.saldo)
                }

                is JuegosEvent.OnTragaMonedas -> {
                    casinoEvent.onNavigateTragaMonedas(usuarioUiState.correo, usuarioUiState.saldo)
                }
            }
        } catch (e: NoNetworkException) {
            Log.e("NoNetworkException", "Error: ${e.localizedMessage}")
            reintentarConexion = true
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
        } catch (e: ConnectException) {
            Log.e("Connect fail", "Error: ${e.localizedMessage}")
        }
    }

    fun crearUsuarioCasino(usuario: CasinoRoute) {
        usuarioUiState = usuarioUiState.copy(
            correo = usuario.correo, saldo = usuario.saldo
        )
    }

    fun onAbrirAyuda() {
        isAyudaAbierta = !isAyudaAbierta
    }
}
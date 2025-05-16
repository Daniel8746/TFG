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
import com.pmdm.casino.ui.features.reiniciarApp
import com.pmdm.casino.ui.features.toJuegosUiStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class JuegosViewModel @Inject constructor(
    private val casinoRepository: JuegosRepository
) : ViewModel() {
    private val _juegosUiState = MutableStateFlow<List<JuegosUiState>>(emptyList())
    val juegosUiState: StateFlow<List<JuegosUiState>> = _juegosUiState.asStateFlow()

    var isAyudaAbierta by mutableStateOf(false)

    var reintentarConexion by mutableStateOf(false)

    var errorApi by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    init {
        viewModelScope.launch {
            casinoRepository.getJuegos()
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
                    _juegosUiState.value = it?.toJuegosUiStates() ?: emptyList()
                }
        }
    }

    fun onCasinoEvent(casinoEvent: JuegosEvent) {
        when (casinoEvent) {
            is JuegosEvent.OnBlackJack -> casinoEvent.onNavigateBlackJack()
            is JuegosEvent.OnRuleta -> casinoEvent.onNavigateRuleta()
            is JuegosEvent.OnTragaMonedas -> casinoEvent.onNavigateTragaMonedas()
        }
    }

    fun onAbrirAyuda() {
        isAyudaAbierta = !isAyudaAbierta
    }
}
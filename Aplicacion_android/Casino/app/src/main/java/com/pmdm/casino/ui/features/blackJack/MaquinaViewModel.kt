package com.pmdm.casino.ui.features.blackJack

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.BlackJackRepository
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.sumarPuntos
import com.pmdm.casino.ui.features.toCartaUiState
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
class MaquinaViewModel @Inject constructor(
    private val blackJackRepository: BlackJackRepository,
) : ViewModel() {
    var puntosTotalesMaquina by mutableIntStateOf(0)

    private val _cartasUiState = MutableStateFlow<MutableList<CartaUiState>>(mutableListOf())
    val cartasUiState: StateFlow<MutableList<CartaUiState>> = _cartasUiState.asStateFlow()

    var finalizarPartida by mutableStateOf(false)

    var errorApi by mutableStateOf(false)

    var reintentarConexion by mutableStateOf(false)

    init {
        pedirCarta()
    }

    fun reiniciarPartida() {
        puntosTotalesMaquina = 0
        finalizarPartida = false
        _cartasUiState.value = mutableListOf()

        pedirCarta()
    }

    suspend fun empezarTurnoMaquina(apuestaJuegoBlackJack: () -> Unit) {
        while (puntosTotalesMaquina < 17) {
            pedirCarta()
            apuestaJuegoBlackJack()
            delay(1500)
        }

        delay(3500)
        plantarse()
    }

    private fun pedirCarta() {
        viewModelScope.launch {
            blackJackRepository.getCarta()
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
                    if (it != null) {
                        _cartasUiState.value.add(it.toCartaUiState())
                    }
                }

            val resultado = sumarPuntos(puntosTotalesMaquina, _cartasUiState.value)

            puntosTotalesMaquina = resultado.first
            finalizarPartida = resultado.second
        }
    }

    private fun plantarse() {
        finalizarPartida = true
    }
}
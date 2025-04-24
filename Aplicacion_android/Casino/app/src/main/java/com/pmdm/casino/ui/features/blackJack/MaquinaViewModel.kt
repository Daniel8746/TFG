package com.pmdm.casino.ui.features.blackJack

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.BlackJackRepository
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.toCartaUiState
import com.pmdm.casino.ui.features.toCartasUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class MaquinaViewModel @Inject constructor(
    private val blackJackRepository: BlackJackRepository
) : ViewModel() {
    var puntosTotalesMaquina by mutableIntStateOf(0)

    private val _cartasUiState = MutableStateFlow<MutableList<CartaUiState>>(mutableListOf())
    val cartasUiState: StateFlow<MutableList<CartaUiState>> = _cartasUiState.asStateFlow()

    var finalizarPartida by mutableStateOf(false)

    init {
        try {
            empezarPartida()
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
        }
    }

    private fun empezarPartida() {
        viewModelScope.launch {
            blackJackRepository.iniciarJuego().collect {
                _cartasUiState.value =
                    it?.toMutableList()?.toCartasUiState() ?: mutableListOf()
            }

            sumarPuntos()
        }
    }

    fun reiniciarPartida() {
        puntosTotalesMaquina = 0
        finalizarPartida = false
        _cartasUiState.value = mutableListOf()

        empezarPartida()
    }

    fun empezarTurnoMaquina(puntosUsuario: Int) {
        var continuar = true

        viewModelScope.launch {
            while (continuar) {
                if (puntosTotalesMaquina >= puntosUsuario || puntosTotalesMaquina >= 21) {
                    plantarse()
                    continuar = false
                } else {
                    pedirCarta()

                    delay(700)
                }
            }
        }
    }

    private fun pedirCarta() {
        viewModelScope.launch {
            blackJackRepository.getCarta().collect {
                if (it != null) {
                    _cartasUiState.value.add(it.toCartaUiState())
                }
            }

            sumarPuntos()
        }
    }

    private fun plantarse() {
        finalizarPartida = true
    }

    private fun sumarPuntos() {
        puntosTotalesMaquina = _cartasUiState.value.fastSumBy { suma ->
            when (suma.valor) {
                "J", "Q", "K" -> {
                    10
                }

                "A" -> {
                    if (puntosTotalesMaquina + 11 > 21) {
                        1
                    } else {
                        11
                    }
                }

                else -> {
                    suma.valor.toInt()
                }
            }
        }

        if (puntosTotalesMaquina >= 21) {
            finalizarPartida = true
        }
    }
}
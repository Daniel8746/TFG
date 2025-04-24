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
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.toCartaUiState
import com.pmdm.casino.ui.features.toCartasUiState
import com.pmdm.casino.ui.navigation.BlackJackRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class BlackJackViewModel @Inject constructor(
    private val blackJackRepository: BlackJackRepository
) : ViewModel() {
    var usuarioUiState by mutableStateOf(UsuarioCasinoUiState())

    private val _cartasUiState = MutableStateFlow<MutableList<CartaUiState>>(mutableListOf())
    val cartasUiState: StateFlow<MutableList<CartaUiState>> = _cartasUiState.asStateFlow()

    private val _cartaRecienteUiState =
        MutableStateFlow<CartaUiState?>(null)
    val cartaRecienteUiState: StateFlow<CartaUiState?> =
        _cartaRecienteUiState.asStateFlow()

    var puntosTotalesUsuario by mutableIntStateOf(0)

    var finalizarPartida by mutableStateOf(false)

    init {
        try {
            empezarPartida()
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
        }
    }

    fun onBlackJackEvent(event: BlackJackEvent) {
        try {
            when (event) {
                is BlackJackEvent.OnPedirCarta -> {
                    viewModelScope.launch {
                        blackJackRepository.getCarta().collect {
                            val nuevaCartaUi = it?.toCartaUiState()

                            _cartaRecienteUiState.value.let { it1 ->
                                if (it1 != null) {
                                    _cartasUiState.value.add(it1)
                                }
                            }

                            if (_cartaRecienteUiState.value != nuevaCartaUi) {
                                _cartaRecienteUiState.value = nuevaCartaUi
                            }
                        }

                        sumarPuntos()
                    }
                }

                is BlackJackEvent.OnPlantarse -> {
                    finalizarPartida = true
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
        } catch (e: ConnectException) {
            Log.e("Connect fail", "Error: ${e.localizedMessage}")
        }
    }

    private fun sumarPuntos() {
        puntosTotalesUsuario = _cartasUiState.value.fastSumBy { suma ->
            getValorCarta(suma)
        }

        puntosTotalesUsuario += _cartaRecienteUiState.value?.let { getValorCarta(it) } ?: 0

        if (puntosTotalesUsuario >= 21) {
            finalizarPartida = true
        }
    }

    // Función que convierte una carta en su valor correspondiente
    private fun getValorCarta(carta: CartaUiState): Int {
        return when (carta.valor) {
            "J", "Q", "K" -> 10
            "A" -> if (puntosTotalesUsuario + 11 > 21) 1 else 11
            else -> carta.valor.toInt()
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
        puntosTotalesUsuario = 0
        finalizarPartida = false
        _cartaRecienteUiState.value = null
        _cartasUiState.value = mutableListOf()

        empezarPartida()
    }

    fun crearUsuarioCasino(usuario: BlackJackRoute) {
        usuarioUiState = usuarioUiState.copy(
            correo = usuario.correo, saldo = usuario.saldo
        )
    }
}
package com.pmdm.casino.ui.features.blackJack

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.BlackJackRepository
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.blackJack.components.CartaUiState
import com.pmdm.casino.ui.features.reiniciarApp
import com.pmdm.casino.ui.features.sumarPuntos
import com.pmdm.casino.ui.features.toCartaUiState
import com.pmdm.casino.ui.features.toCartasUiState
import com.pmdm.casino.ui.navigation.BlackJackRoute
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

    var reintentarConexion by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    var poderPulsarBoton by mutableStateOf(true)

    init {
        empezarPartida()
    }

    fun onBlackJackEvent(event: BlackJackEvent) {
        poderPulsarBoton = false
        when (event) {
            is BlackJackEvent.OnPedirCarta -> {
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
                                }

                                is ConnectException -> {
                                    Log.e("Connect fail", "Error: ${e.localizedMessage}")
                                }
                            }
                        }.collect {
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

                    val resultado =
                        sumarPuntos(
                            puntosTotalesUsuario,
                            _cartasUiState.value,
                            _cartaRecienteUiState.value
                        )

                    puntosTotalesUsuario = resultado.first
                    finalizarPartida = resultado.second
                }
            }

            is BlackJackEvent.OnPlantarse -> {
                finalizarPartida = true
            }
        }
        poderPulsarBoton = true
    }

    private fun empezarPartida() {
        viewModelScope.launch {
            blackJackRepository.iniciarJuego()
                .catch { e ->
                    when (e) {
                        is NoNetworkException -> {
                            Log.e("NoNetworkException", "Error: ${e.localizedMessage}")
                            reintentarConexion = true
                        }

                        is SocketTimeoutException -> {
                            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
                        }

                        is ConnectException -> {
                            Log.e("Connect fail", "Error: ${e.localizedMessage}")
                        }
                    }
                }.collect {
                    _cartasUiState.value =
                        it?.toMutableList()?.toCartasUiState() ?: mutableListOf()
                }

            val resultado =
                sumarPuntos(
                    puntosTotalesUsuario,
                    _cartasUiState.value,
                    _cartaRecienteUiState.value
                )

            puntosTotalesUsuario = resultado.first
        }
    }

    fun reiniciarPartida() {
        puntosTotalesUsuario = 0
        finalizarPartida = false
        _cartaRecienteUiState.value = null
        _cartasUiState.value = mutableListOf()

        empezarPartida()
    }

    fun reiniciarCartas() {
        viewModelScope.launch {
            blackJackRepository.reiniciarCartas()
        }
    }

    fun crearUsuarioCasino(usuario: BlackJackRoute) {
        usuarioUiState = usuarioUiState.copy(
            correo = usuario.correo, saldo = usuario.saldo
        )
    }
}
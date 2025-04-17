package com.pmdm.casino.ui.features.blackJack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var sumaTotalPuntos = 0

    init {
        viewModelScope.launch {
            blackJackRepository.iniciarJuego().collect {
                _cartasUiState.value = it?.toMutableList()?.toCartasUiState() ?: mutableListOf()
            }
        }
    }

    fun onBlackJackEvent(event: BlackJackEvent) {
        when (event) {
            is BlackJackEvent.OnPedirCarta -> {
                viewModelScope.launch {
                    blackJackRepository.getCarta().collect {
                        if (!cartaRecienteUiState.equals(it)) {
                            _cartaRecienteUiState.value = it?.toCartaUiState()
                        }
                    }
                }
            }

            is BlackJackEvent.OnPlantarse -> {

            }
        }
    }

    fun crearUsuarioCasino(usuario: BlackJackRoute) {
        usuarioUiState = usuarioUiState.copy(
            correo = usuario.correo, saldo = usuario.saldo
        )
    }
}
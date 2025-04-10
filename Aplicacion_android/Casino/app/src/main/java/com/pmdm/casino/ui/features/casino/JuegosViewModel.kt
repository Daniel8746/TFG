package com.pmdm.casino.ui.features.casino

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.JuegosRepository
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.features.toJuegosUiStates
import com.pmdm.casino.ui.navigation.CasinoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JuegosViewModel @Inject constructor(
    private val casinoRepository: JuegosRepository
): ViewModel() {
    var usuarioUiState by mutableStateOf(UsuarioCasinoUiState())
    var juegosUiState = mutableStateListOf<JuegosUiState>()
    var isAyudaAbierta by mutableStateOf(false)

    init {
        viewModelScope.launch {
            casinoRepository.getJuegos().collect {
                it?.toJuegosUiStates()?.forEach { juego ->
                    juegosUiState.add(juego)
                }
            }
        }
    }

    fun onCasinoEvent(casinoEvent: JuegosEvent) {
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
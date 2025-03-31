package com.pmdm.casino.ui.features.casino

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.JuegosRepository
import com.pmdm.casino.ui.features.toCasinoUiStates
import com.pmdm.casino.ui.navigation.CasinoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CasinoViewModel @Inject constructor(
    private val casinoRepository: JuegosRepository
): ViewModel() {
    var usuarioUiState by mutableStateOf(UsuarioCasinoUiState())
    var juegosUiState: List<CasinoUiState> = listOf()
    var isAyudaAbierta = false


    init {
        viewModelScope.launch {
            casinoRepository.getJuegos().collect {
                juegosUiState = it?.toCasinoUiStates() ?: listOf()
            }
        }

    }

    fun onCasinoEvent(casinoEvent: CasinoEvent) {
        when (casinoEvent) {
            is CasinoEvent.OnBlackJack -> {
                casinoEvent.onNavigateBlackJack(usuarioUiState.correo, usuarioUiState.saldo)
            }
            is CasinoEvent.OnRuleta -> {
                casinoEvent.onNavigateRuleta(usuarioUiState.correo, usuarioUiState.saldo)
            }
            is CasinoEvent.OnTragaMonedas -> {
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
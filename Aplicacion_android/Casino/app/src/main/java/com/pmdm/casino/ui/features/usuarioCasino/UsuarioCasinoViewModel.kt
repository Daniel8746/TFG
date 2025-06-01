package com.pmdm.casino.ui.features.usuarioCasino

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.repositorys.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class UsuarioCasinoViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
) : ViewModel() {

    var usuarioCasinoUiState by mutableStateOf(UsuarioCasinoUiState())
        private set

    var partidaEmpezadaBlackJack by mutableStateOf(false)

    fun onUsuarioCasinoEvent(event: UsuarioCasinoEvent) {
        when (event) {
            is UsuarioCasinoEvent.AumentarSaldo -> actualizarUsuarioCasino(
                correo = usuarioCasinoUiState.correo,
                saldo = usuarioCasinoUiState.saldo + event.saldo
            )

            is UsuarioCasinoEvent.BajarSaldo -> actualizarUsuarioCasino(
                correo = usuarioCasinoUiState.correo,
                saldo = usuarioCasinoUiState.saldo - event.saldo
            )
        }
    }

    fun setEstadoPartida(juego: String, activa: Boolean) {
        when (juego) {
            "Blackjack" -> partidaEmpezadaBlackJack = activa
        }
    }

    fun actualizarUsuarioCasino(correo: String, saldo: BigDecimal) {
        if (correo != usuarioCasinoUiState.correo || saldo != usuarioCasinoUiState.saldo) {
            usuarioCasinoUiState = usuarioCasinoUiState.copy(
                correo = correo,
                saldo = saldo
            )
        }
    }

    fun actualizarSaldoUsuario() {
        if (usuarioCasinoUiState.correo.isNotEmpty()) {
            viewModelScope.launch {
                usuarioRepository.actualizarSaldo(
                    correo = usuarioCasinoUiState.correo,
                    saldo = usuarioCasinoUiState.saldo
                )
            }
        }
    }
}
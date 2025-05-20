package com.pmdm.casino.ui.features.usuarioCasino

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class UsuarioCasinoViewModel @Inject constructor() : ViewModel() {

    var usuarioCasinoUiState by mutableStateOf(UsuarioCasinoUiState())
        private set

    var partidaEmpezadaBlackJack by mutableStateOf(false)
    private var partidaEmpezadaRuleta by mutableStateOf(false)
    private var partidaEmpezadaTragaMonedas by mutableStateOf(false)

    fun onUsuarioCasinoEvent(event: UsuarioCasinoEvent) {
        when (event) {
            is UsuarioCasinoEvent.AumentarSaldo -> actualizarUsuarioCasino(
                correo = usuarioCasinoUiState.correo,
                saldo = usuarioCasinoUiState.saldo + event.saldo
            )

            is UsuarioCasinoEvent.BajarSaldo -> if (
                !partidaEmpezadaBlackJack ||
                !partidaEmpezadaRuleta ||
                !partidaEmpezadaTragaMonedas
            ) {
                actualizarUsuarioCasino(
                    correo = usuarioCasinoUiState.correo,
                    saldo = usuarioCasinoUiState.saldo - event.saldo
                )
            }
        }
    }

    fun setEstadoPartida(juego: String, activa: Boolean) {
        when (juego) {
            "Blackjack" -> partidaEmpezadaBlackJack = activa
            "Ruleta" -> partidaEmpezadaRuleta = activa
            "Tragamonedas" -> partidaEmpezadaTragaMonedas = activa
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
}
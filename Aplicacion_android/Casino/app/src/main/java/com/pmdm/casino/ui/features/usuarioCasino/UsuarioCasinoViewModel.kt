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

    fun onUsuarioCasinoEvent(event: UsuarioCasinoEvent) {
        usuarioCasinoUiState = when (event) {
            is UsuarioCasinoEvent.AumentarSaldo -> usuarioCasinoUiState.copy(saldo = usuarioCasinoUiState.saldo + event.saldo)

            is UsuarioCasinoEvent.BajarSaldo -> usuarioCasinoUiState.copy(saldo = usuarioCasinoUiState.saldo - event.saldo)
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
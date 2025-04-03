package com.pmdm.casino.ui.features.tragaMonedas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.navigation.TragaMonedasRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TragaMonedasViewModel @Inject constructor(

): ViewModel() {
    var usuarioUiState by mutableStateOf(UsuarioCasinoUiState())

    fun crearUsuarioCasino(usuario: TragaMonedasRoute) {
        usuarioUiState = usuarioUiState.copy(
            correo = usuario.correo, saldo = usuario.saldo
        )
    }
}
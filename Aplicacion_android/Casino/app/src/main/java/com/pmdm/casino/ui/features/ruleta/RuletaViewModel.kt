package com.pmdm.casino.ui.features.ruleta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pmdm.casino.ui.features.UsuarioCasinoUiState
import com.pmdm.casino.ui.navigation.RuletaRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RuletaViewModel @Inject constructor(

): ViewModel() {
    var usuarioUiState by mutableStateOf(UsuarioCasinoUiState())

    fun crearUsuarioCasino(usuario: RuletaRoute) {
        usuarioUiState = usuarioUiState.copy(
            correo = usuario.correo, saldo = usuario.saldo
        )
    }
}
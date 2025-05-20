package com.pmdm.casino.ui.features.dialogoErrorNoSaldo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogoErrorNoSaldoViewModel @Inject constructor() : ViewModel() {
    var mostrarDialogoErrorNoSaldo by mutableStateOf(false)

    fun onMostrarDialogo() {
        mostrarDialogoErrorNoSaldo = !mostrarDialogoErrorNoSaldo
    }
}
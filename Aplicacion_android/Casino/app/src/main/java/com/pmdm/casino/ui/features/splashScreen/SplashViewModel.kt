package com.pmdm.casino.ui.features.splashScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.repositorys.UsuarioRepository
import com.pmdm.casino.model.Usuario
import com.pmdm.casino.ui.features.reiniciarApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
) : ViewModel() {
    private val _saldo = MutableStateFlow(BigDecimal(0))
    val saldoState: StateFlow<BigDecimal> = _saldo.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correoState: StateFlow<String> = _correo.asStateFlow()

    var reintentarConexion by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    init {
        viewModelScope.launch {
            comprobarToken()
        }
    }

    private suspend fun comprobarToken() {
        val resultado = usuarioRepository.login(Usuario())

        resultado.collect {
            if (it.first) {
                _saldo.value = it.second
                _correo.value = it.third
            }
        }
    }
}
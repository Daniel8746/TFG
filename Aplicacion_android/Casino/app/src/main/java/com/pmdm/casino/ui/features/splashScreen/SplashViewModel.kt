package com.pmdm.casino.ui.features.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.UsuarioRepository
import com.pmdm.casino.model.Usuario
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
): ViewModel() {
    private val _saldo = MutableStateFlow(BigDecimal(0))
    val saldoState: StateFlow<BigDecimal> = _saldo.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correoState: StateFlow<String> = _correo.asStateFlow()

    fun comprobarToken() {
        viewModelScope.launch {
            val resultado = usuarioRepository.login(Usuario())

            resultado.collect{
                if (it.first) {
                    _saldo.value = it.second
                    _correo.value = it.third
                }
            }
        }
    }
}
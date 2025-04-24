package com.pmdm.casino.ui.features.splashScreen

import android.util.Log
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
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
) : ViewModel() {
    private val _saldo = MutableStateFlow(BigDecimal(0))
    val saldoState: StateFlow<BigDecimal> = _saldo.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correoState: StateFlow<String> = _correo.asStateFlow()

    fun comprobarToken() {
        try {
            viewModelScope.launch {
                val resultado = usuarioRepository.login(Usuario())

                resultado.collect {
                    if (it.first) {
                        _saldo.value = it.second
                        _correo.value = it.third
                    }
                }
            }
        } catch (e: SocketTimeoutException) {
            Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
        } catch (e: ConnectException) {
            Log.e("Connect fail", "Error: ${e.localizedMessage}")
        }
    }
}
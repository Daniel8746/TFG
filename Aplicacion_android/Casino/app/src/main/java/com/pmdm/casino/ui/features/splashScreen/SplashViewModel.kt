package com.pmdm.casino.ui.features.splashScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.UsuarioRepository
import com.pmdm.casino.model.Usuario
import com.pmdm.casino.ui.features.reiniciarApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    var reintentarConexion by mutableStateOf(false)

    var errorApi by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    init {
        viewModelScope.launch {
            comprobarToken()
        }
    }

    private suspend fun comprobarToken() {
        usuarioRepository.login(Usuario())
            .catch { e ->
                when (e) {
                    is NoNetworkException -> {
                        Log.e("NoNetworkException", "Error: ${e.localizedMessage}")
                        reintentarConexion = true
                    }

                    is SocketTimeoutException -> {
                        Log.e("SocketTimeOut", "Error: ${e.localizedMessage}")
                        errorApi = true
                    }

                    is ConnectException -> {
                        Log.e("Connect fail", "Error: ${e.localizedMessage}")
                        errorApi = true
                    }
                }
            }.collect {
                if (it.first) {
                    _saldo.value = it.second
                    _correo.value = it.third
                }
            }
    }
}
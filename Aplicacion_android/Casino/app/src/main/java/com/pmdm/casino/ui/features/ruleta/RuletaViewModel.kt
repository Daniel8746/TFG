package com.pmdm.casino.ui.features.ruleta

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.RuletaRepository
import com.pmdm.casino.ui.features.reiniciarApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import java.math.BigDecimal
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class RuletaViewModel @Inject constructor(
    private val ruletaRepository: RuletaRepository
) : ViewModel() {
    var reintentarConexion by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }

    var errorApi by mutableStateOf(false)

    var listaApuestaMarcado: List<ApuestasUiState> by mutableStateOf(listOf())

    private var _listaApuestaDefinitiva =
        MutableStateFlow<Map<ApuestasUiState, BigDecimal>>(hashMapOf())
    var listaApuestaDefinitiva: StateFlow<Map<ApuestasUiState, BigDecimal>> =
        _listaApuestaDefinitiva.asStateFlow()

    private var _tiempoContador = MutableStateFlow(5000)
    var tiempoContador: StateFlow<Int> = _tiempoContador.asStateFlow()

    var apostado by mutableStateOf(1.toBigDecimal())

    var numeroRuleta by mutableIntStateOf(-1)

    init {
        viewModelScope.launch {
            ruletaRepository.getContador()
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
                    _tiempoContador.value = it
                }
        }
    }

    fun onRuletaEvent(event: RuletaEvent) {
        viewModelScope.launch {
            when (event) {
                is RuletaEvent.ApuestaChanged -> apostado = event.apuesta

                RuletaEvent.FlechaSubirPulsado -> {
                    apostado = when (apostado) {
                        1.toBigDecimal() -> 5.toBigDecimal()
                        else -> apostado + 5.toBigDecimal()
                    }
                }

                RuletaEvent.FlechaBajarPulsado -> apostado -= 5.toBigDecimal()

                is RuletaEvent.ApuestaSeleccionada -> {
                    listaApuestaMarcado = if (event.apuestasUiState in listaApuestaMarcado) {
                        listaApuestaMarcado - event.apuestasUiState
                    } else {
                        listaApuestaMarcado + event.apuestasUiState
                    }
                }

                is RuletaEvent.Apostar -> {
                    val nuevaApuesta = listaApuestaMarcado.associateWith { event.apuestaUsuario }

                    val copiaActualizada = _listaApuestaDefinitiva.value.toMutableMap()

                    nuevaApuesta.forEach { (clave, nuevoValor) ->
                        copiaActualizada[clave] = nuevoValor
                    }

                    _listaApuestaDefinitiva.value = copiaActualizada.toMap()
                }

                RuletaEvent.QuitarApuesta -> {
                    _listaApuestaDefinitiva.value =
                        _listaApuestaDefinitiva.value.filter { it.key !in listaApuestaMarcado }
                }

                RuletaEvent.FinalizarJuego -> {
                    ruletaRepository.getNumeroRuleta().catch { e ->
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
                        numeroRuleta = it
                    }

                    notifyAll()
                }
            }

            if (apostado < 1.toBigDecimal()) {
                apostado = 1.toBigDecimal()
            }
        }
    }
}
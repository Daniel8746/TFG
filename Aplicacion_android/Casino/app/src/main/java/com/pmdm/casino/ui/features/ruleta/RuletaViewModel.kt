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
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class RuletaViewModel @Inject constructor(
    private val ruletaRepository: RuletaRepository
) : ViewModel() {
    var poderPulsarBoton by mutableStateOf(true)

    var reintentarConexion by mutableStateOf(false)

    fun reiniciar(context: Context) {
        reintentarConexion = reiniciarApp(context)
    }
    
    var errorApi by mutableStateOf(false)

    private var listaNumerosApostar: List<String> by mutableStateOf(listOf())

    private var _tiempoContador = MutableStateFlow(5000)
    var tiempoContador: StateFlow<Int> = _tiempoContador.asStateFlow()

    var apostado by mutableIntStateOf(1)

    fun onRuletaEvent(event: RuletaEvent) {
        viewModelScope.launch {
            when (event) {
                is RuletaEvent.ApuestaChanged -> apostado = event.apuesta

                RuletaEvent.FlechaSubirPulsado -> {
                    apostado = when (apostado) {
                        1 -> 5
                        else -> apostado + 10
                    }
                }

                RuletaEvent.FlechaBajarPulsado -> apostado -= 5

                is RuletaEvent.ApuestaSeleccionada -> {
                    listaNumerosApostar = if (event.numero in listaNumerosApostar) {
                        listaNumerosApostar - event.numero
                    } else {
                        listaNumerosApostar + event.numero
                    }
                }

                RuletaEvent.Apostar -> {

                }

                RuletaEvent.QuitarApuesta -> {

                }

                RuletaEvent.EntrarJuego -> ruletaRepository.getContador()
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

                RuletaEvent.FinalizarJuego -> ruletaRepository.reiniciarContador()
            }

            if (apostado < 1) {
                apostado = 1
            }
        }
    }
}
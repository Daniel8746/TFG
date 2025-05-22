package com.pmdm.casino.ui.features.apuestas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmdm.casino.data.repositorys.ApuestasRepository
import com.pmdm.casino.model.Apuesta
import com.pmdm.casino.ui.features.blackJack.DetallesBlackJack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class ApuestasViewModel @Inject constructor(
    private val apuestasRepository: ApuestasRepository
) : ViewModel() {
    private var correoUsuario by mutableStateOf("")
    private var nombreJuegos by mutableStateOf("")
    private var montoApuesta by mutableStateOf(0.toBigDecimal())
    private var resultados by mutableStateOf("")
    private var detallesResultado by mutableStateOf("")

    fun finalizarBlackJack(
        resultado: String,
        detalles: DetallesBlackJack
    ) {
        val detallesJson = Json.encodeToString(detalles)

        finalizar(resultado, detallesJson)
    }

    fun finalizar(
        resultado: String,
        detalles: String
    ) {
        if (correoUsuario.isNotBlank()) {
            viewModelScope.launch {
                apuestasRepository.finalizar(
                    Apuesta(
                        correoUsuario = correoUsuario,
                        nombreJuego = nombreJuegos,
                        montoApostado = montoApuesta,
                        resultado = resultado,
                        detallesResultado = detalles
                    )
                )
            }
        }
    }

    private fun apuesta(
        correo: String,
        nombreJuego: String,
        montoApostado: BigDecimal,
        resultado: String,
        detalles: String
    ) {
        if (correo != correoUsuario) correoUsuario = correo
        if (nombreJuego != nombreJuegos) nombreJuegos = nombreJuego
        if (montoApostado != montoApuesta) montoApuesta = montoApostado
        if (resultado != resultados) resultados = resultado
        if (detalles != detallesResultado) detallesResultado = detalles

        viewModelScope.launch {
            apuestasRepository.apuestaJuego(
                Apuesta(
                    correoUsuario = correoUsuario,
                    nombreJuego = nombreJuegos,
                    montoApostado = montoApuesta,
                    resultado = resultados,
                    detallesResultado = detallesResultado
                )
            )
        }
    }

    fun apuestaJuegoBlackJack(
        correo: String,
        nombreJuego: String,
        montoApostado: BigDecimal,
        resultado: String,
        detalles: DetallesBlackJack
    ) {
        val detallesJson = Json.encodeToString(detalles)

        apuesta(
            correo = correo,
            nombreJuego = nombreJuego,
            montoApostado = montoApostado,
            resultado = resultado,
            detalles = detallesJson
        )
    }
}
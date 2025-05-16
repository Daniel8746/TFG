package com.pmdm.casino.data.services.blackJack

import android.util.Log
import com.pmdm.casino.data.repositorys.validarCodigoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlackJackServiceImplementation @Inject constructor(
    private val blackJackService: BlackJackService
) {
    private val logTag: String = "OkHttp"

    suspend fun getCarta(): CartaApi? {
        val mensajeError = "No se ha podido obtener la carta"

        try {
            val response = blackJackService.getCarta()

            validarCodigoResponse(response)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())
                Log.d(
                    logTag,
                    response.body()?.toString() ?: "No hay respuesta"
                )
            } else {
                val body = response.errorBody()?.toString()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
            }

            return response.body()
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            return null
        }
    }

    suspend fun iniciarJuego(): List<CartaApi>? {
        val mensajeError = "No se ha podido obtener las cartas"

        try {
            val response = blackJackService.iniciarJuego()

            validarCodigoResponse(response)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())

                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.toString()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
            }

            return response.body()
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            return null
        }
    }

    suspend fun reiniciarCartas() {
        val mensajeError = "No se ha podido finalizar correctamente el juego"

        try {
            val response = blackJackService.finalizarJuego()

            validarCodigoResponse(response)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())

                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.toString()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
            }
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
        }
    }
}
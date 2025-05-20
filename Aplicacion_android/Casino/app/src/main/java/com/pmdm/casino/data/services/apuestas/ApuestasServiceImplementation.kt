package com.pmdm.casino.data.services.apuestas

import android.util.Log
import com.pmdm.casino.data.repositorys.validarCodigoResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApuestasServiceImplementation @Inject constructor(
    private val apuestasService: ApuestasService
) {
    private val logTag: String = "OkHttp"

    suspend fun finalizar(apuesta: ApuestaApiRecord) {
        val mensajeError = "No se ha podido finalizar el juego"

        try {
            val response = apuestasService.finalizar(apuesta)

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

    suspend fun apuestaJuego(apuesta: ApuestaApiRecord) {
        val mensajeError = "No se ha podido iniciar la apuesta"

        try {
            val response = apuestasService.apuestaJuego(apuesta)

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
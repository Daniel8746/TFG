package com.pmdm.casino.data.services.ruleta

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuletaServiceImplementation @Inject constructor(
    private val ruletaService: RuletaService
) {
    private val logTag: String = "OkHttp"

    suspend fun getContador(): Int {
        val mensajeError = "No se ha podido obtener el contador"

        try {
            val response = ruletaService.getContador()

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

            return response.body() ?: 0
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            return 0
        }
    }

    suspend fun reiniciarContador() {
        val mensajeError = "No se ha podido obtener el contador"

        try {
            val response = ruletaService.reiniciarContador()

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
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
        }
    }
}
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

    suspend fun finalizarBlackJack(): Boolean {
        val mensajeError = "No se ha podido obtener el usuario"

        try {
            val response = apuestasService.finalizarBlackJack()

            validarCodigoResponse(response)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())

                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.toString()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
            }

            return response.isSuccessful
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            return true
        }
    }
}
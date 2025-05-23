package com.pmdm.casino.data.services.ruleta

import android.util.Log
import com.pmdm.casino.data.exceptions.NoNetworkException
import com.pmdm.casino.data.repositorys.validarCodigoResponse
import java.net.ConnectException
import java.net.SocketTimeoutException
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

            return response.body() ?: 0
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")

            when (e) {
                is NoNetworkException -> throw NoNetworkException("No Network")
                is SocketTimeoutException -> throw SocketTimeoutException("Finalizado tiempo espera")
                is ConnectException -> throw ConnectException("Error al conectar")
                else -> throw Exception(e.localizedMessage)
            }
        }
    }

    suspend fun getNumeroRuleta(): Int {
        val mensajeError = "No se ha podido obtener el contador"

        try {
            val response = ruletaService.getNumeroRuleta()

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

            return response.body() ?: 0
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")

            when (e) {
                is NoNetworkException -> throw NoNetworkException("No Network")
                is SocketTimeoutException -> throw SocketTimeoutException("Finalizado tiempo espera")
                is ConnectException -> throw ConnectException("Error al conectar")
                else -> throw Exception(e.localizedMessage)
            }
        }
    }
}
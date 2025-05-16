package com.pmdm.casino.data.services.usuario

import android.util.Log
import com.pmdm.casino.data.repositorys.validarCodigoResponse
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioServiceImplementation @Inject constructor(
    private val usuarioService: UsuarioService
) {
    private val logTag: String = "OkHttp"

    suspend fun login(usuario: UsuarioApiRecord): Triple<Boolean, BigDecimal, String> {
        val mensajeError = "No se ha podido obtener el usuario"

        try {
            val response = usuarioService.login(usuario)

            validarCodigoResponse(response)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())

                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.toString()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
            }

            return Triple(
                response.isSuccessful,
                response.body()?.saldo ?: BigDecimal(0),
                response.body()?.token ?: ""
            )
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            return Triple(false, BigDecimal(0), "")
        }
    }

    suspend fun crearUsuario(usuario: NuevoUsuarioApi): Boolean {
        val mensajeError = "No se ha podido crear el usuario"

        try {
            val response = usuarioService.crearUsuario(usuario)

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
            return false
        }
    }

    suspend fun eliminarUsuario(usuario: NuevoUsuarioApi): Boolean {
        val mensajeError = "No se ha podido eliminar el usuario"

        try {
            val response = usuarioService.eliminarUsuario(usuario)

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
            return false
        }
    }
}
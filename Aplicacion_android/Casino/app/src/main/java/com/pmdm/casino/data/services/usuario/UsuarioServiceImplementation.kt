package com.pmdm.casino.data.services.usuario

import android.util.Log
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioServiceImplementation @Inject constructor(
    private val usuarioService: UsuarioService
) {
    private val logTag: String = "OkHttp"

    suspend fun getSaldo(correo: String): BigDecimal {
        val mensajeError = "No se ha podido obtener el usuario"

        try {
            val response = usuarioService.getSaldo(correo)

            if (response.isSuccessful) {
                Log.d(logTag, response.toString())

                Log.d(logTag, response.body()?.toString() ?: "No hay respuesta")
            } else {
                val body = response.errorBody()?.toString()
                Log.e(logTag, "$mensajeError (código ${response.code()}): $this\n${body}")
            }

            return response.body()?.saldo ?: BigDecimal(0)
        } catch (e: Exception) {
            Log.e(logTag, "Error: ${e.localizedMessage}")
            return BigDecimal(0)
        }
    }

    suspend fun login(usuario: UsuarioApiRecord): Boolean {
        val mensajeError = "No se ha podido obtener el usuario"

        try {
            val response = usuarioService.login(usuario)

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

    suspend fun crearUsuario(usuario: UsuarioApi): Boolean {
        val mensajeError = "No se ha podido crear el usuario"

        try {
            val response = usuarioService.crearUsuario(usuario)

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

    suspend fun eliminarUsuario(usuario: UsuarioApi): Boolean {
        val mensajeError = "No se ha podido eliminar el usuario"

        try {
            val response = usuarioService.eliminarUsuario(usuario)

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
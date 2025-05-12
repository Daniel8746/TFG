package com.pmdm.casino.data.services.interceptors

import android.content.Context
import android.util.Log
import com.pmdm.casino.model.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Obtener el token desde DataStore
        val token: String? = runBlocking {
            withContext(Dispatchers.IO) {
                TokenManager.getToken(context)
            }
        }

        Log.d(
            "AuthInterceptor",
            "Token: $token"
        )  // Log para verificar si se ejecuta el interceptor

        // Si el token es válido, agregarlo al encabezado
        val requestBuilder = chain.request().newBuilder()

        token.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        // Mostrar el encabezado en el log
        Log.d("Request", "Authorization Header: " + requestBuilder.build().header("Authorization"))

        // Continuar con la solicitud
        return chain.proceed(requestBuilder.build())
    }
}
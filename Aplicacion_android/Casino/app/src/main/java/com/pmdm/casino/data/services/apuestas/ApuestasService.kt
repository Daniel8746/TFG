package com.pmdm.casino.data.services.apuestas

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApuestasService {
    @POST("apuestas/finalizar")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun finalizar(@Body apuesta: ApuestaApiRecord): Response<Unit>

    @POST("apuestas/apuesta-juego")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun apuestaJuego(@Body apuesta: ApuestaApiRecord): Response<Unit>
}
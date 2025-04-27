package com.pmdm.casino.data.services.apuestas

import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApuestasService {
    @POST("apuestas/finalizar/black-jack")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun finalizarBlackJack(): Response<Unit>
}
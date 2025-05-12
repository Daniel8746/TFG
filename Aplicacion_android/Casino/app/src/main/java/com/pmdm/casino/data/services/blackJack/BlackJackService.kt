package com.pmdm.casino.data.services.blackJack

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface BlackJackService {
    @GET("black-jack/carta")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getCarta(): Response<CartaApi>

    @GET("black-jack/iniciarJuego")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun iniciarJuego(): Response<List<CartaApi>>

    @GET("black-jack/reiniciar-cartas")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun finalizarJuego(): Response<Unit>
}
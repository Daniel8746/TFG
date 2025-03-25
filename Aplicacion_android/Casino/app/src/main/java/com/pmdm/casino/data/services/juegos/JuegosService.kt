package com.pmdm.casino.data.services.juegos

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface JuegosService {
    @GET("/todos-juegos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getJuegos(): Response<List<JuegosApi>>
}
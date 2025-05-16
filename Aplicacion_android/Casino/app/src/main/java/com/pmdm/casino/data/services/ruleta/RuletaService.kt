package com.pmdm.casino.data.services.ruleta

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RuletaService {
    @GET("ruleta/contador")
    @Headers("Accept: application/json")
    suspend fun getContador(): Response<Int>

    @GET("ruleta/reiniciar")
    @Headers("Accept: application/json")
    suspend fun reiniciarContador(): Response<Unit>
}
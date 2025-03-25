package com.pmdm.casino.data.services.usuario

import com.pmdm.casino.data.services.RespuestaApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioService {
    @GET("/usuario/{correo}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getSaldo(@Path("correo") correo: String): Response<RespuestaApi>

    @POST("crear-usuario")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun crearUsuario(@Body usuario: UsuarioApi): Response<Unit>

    @POST("login")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun login(@Body usuario: UsuarioApiRecord): Response<Unit>

    @DELETE("eliminar")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun eliminarUsuario(@Body usuario: UsuarioApi): Response<Unit>
}
package com.pmdm.casino.data.services.usuario

import com.pmdm.casino.data.services.RespuestaApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST

interface UsuarioService {
    @POST("/usuario/crear-usuario")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun crearUsuario(@Body usuario: UsuarioApi): Response<Unit>

    @POST("/usuario/login")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun login(@Body usuario: UsuarioApiRecord): Response<RespuestaApi>

    @DELETE("/usuario/eliminar")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun eliminarUsuario(@Body usuario: UsuarioApi): Response<Unit>
}
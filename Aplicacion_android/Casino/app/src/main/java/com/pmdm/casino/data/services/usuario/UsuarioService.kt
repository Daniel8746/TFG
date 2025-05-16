package com.pmdm.casino.data.services.usuario

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST

interface UsuarioService {
    @POST("usuario/crear-usuario")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun crearUsuario(@Body usuario: NuevoUsuarioApi): Response<Unit>

    @POST("usuario/login")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun login(@Body usuario: UsuarioApiRecord): Response<UsuarioRespuestaApi>

    @DELETE("usuario/eliminar")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun eliminarUsuario(@Body usuario: NuevoUsuarioApi): Response<Unit>
}
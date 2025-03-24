package com.pmdm.casino.data

import com.pmdm.casino.data.services.usuario.UsuarioServiceImplementation
import com.pmdm.casino.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val usuarioService: UsuarioServiceImplementation
) {
    suspend fun login(usuario: Usuario): Flow<Boolean> = flow {
        emit(usuarioService.login(usuario.toUsuarioApiRecord()))
    }.flowOn(Dispatchers.IO)

    suspend fun crear(usuario: Usuario): Flow<Boolean> = flow {
        emit(usuarioService.crearUsuario(usuario.toUsuarioApi()))
    }.flowOn(Dispatchers.IO)

    suspend fun eliminar(usuario: Usuario): Flow<Boolean> = flow {
        emit(usuarioService.eliminarUsuario(usuario.toUsuarioApi()))
    }.flowOn(Dispatchers.IO)
}
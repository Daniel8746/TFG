package com.pmdm.casino.data

import com.pmdm.casino.data.services.juegos.JuegosServiceImplementation
import com.pmdm.casino.model.Juegos
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class JuegosRepository @Inject constructor(
    private val juegosService: JuegosServiceImplementation
) {
    suspend fun getJuegos(): Flow<List<Juegos>?> = flow {
        emit(juegosService.getJuegos()?.toJuegos())
    }.flowOn(Dispatchers.IO)
}
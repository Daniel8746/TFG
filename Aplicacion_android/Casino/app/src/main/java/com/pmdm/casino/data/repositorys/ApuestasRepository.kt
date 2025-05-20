package com.pmdm.casino.data.repositorys

import com.pmdm.casino.data.services.apuestas.ApuestasServiceImplementation
import com.pmdm.casino.model.Apuesta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApuestasRepository @Inject constructor(
    private val apuestasService: ApuestasServiceImplementation
) {
    suspend fun finalizar(apuesta: Apuesta) = flow {
        emit(apuestasService.finalizar(apuesta.toApuestaApi()))
    }.flowOn(Dispatchers.IO)

    suspend fun apuestaJuego(apuesta: Apuesta) = flow {
        emit(apuestasService.apuestaJuego(apuesta.toApuestaApi()))
    }.flowOn(Dispatchers.IO)
}
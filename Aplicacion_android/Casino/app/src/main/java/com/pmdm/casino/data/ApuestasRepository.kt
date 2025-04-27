package com.pmdm.casino.data

import com.pmdm.casino.data.services.apuestas.ApuestasServiceImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApuestasRepository @Inject constructor(
    private val apuestasService: ApuestasServiceImplementation
) {
    suspend fun finalizarBlackJack(): Flow<Boolean> = flow {
        emit(apuestasService.finalizarBlackJack())
    }.flowOn(Dispatchers.IO)
}
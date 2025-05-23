package com.pmdm.casino.data.repositorys

import com.pmdm.casino.data.services.ruleta.RuletaServiceImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RuletaRepository @Inject constructor(
    private val ruletaService: RuletaServiceImplementation
) {
    suspend fun getContador(): Flow<Int> = flow {
        emit(ruletaService.getContador())
    }.flowOn(Dispatchers.IO)

    suspend fun getNumeroRuleta(): Flow<Int> = flow {
        emit(ruletaService.getNumeroRuleta())
    }.flowOn(Dispatchers.IO)
}
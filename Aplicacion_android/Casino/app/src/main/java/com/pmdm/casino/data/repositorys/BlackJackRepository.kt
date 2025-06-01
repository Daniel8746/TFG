package com.pmdm.casino.data.repositorys

import com.pmdm.casino.data.services.blackJack.BlackJackServiceImplementation
import com.pmdm.casino.model.Carta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BlackJackRepository @Inject constructor(
    private val blackJackService: BlackJackServiceImplementation
) {
    fun getCarta(): Flow<Carta?> = flow {
        emit(blackJackService.getCarta()?.toCarta())
    }.flowOn(Dispatchers.IO)

    fun iniciarJuego(): Flow<List<Carta>?> = flow {
        emit(blackJackService.iniciarJuego()?.toCartas())
    }.flowOn(Dispatchers.IO)

    suspend fun reiniciarCartas() = blackJackService.reiniciarCartas()
}
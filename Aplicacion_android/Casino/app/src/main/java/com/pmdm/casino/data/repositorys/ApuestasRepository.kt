package com.pmdm.casino.data.repositorys

import com.pmdm.casino.data.services.apuestas.ApuestasServiceImplementation
import com.pmdm.casino.model.Apuesta
import javax.inject.Inject

class ApuestasRepository @Inject constructor(
    private val apuestasService: ApuestasServiceImplementation
) {
    suspend fun finalizar(apuesta: Apuesta) = apuestasService.finalizar(apuesta.toApuestaApi())

    suspend fun apuestaJuego(apuesta: Apuesta) =
        apuestasService.apuestaJuego(apuesta.toApuestaApi())
}
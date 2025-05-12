package com.pmdm.casino.data.repositorys

import com.pmdm.casino.data.exceptions.NoNetworkException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

fun <T> validarCodigoResponse(response: Response<T>) {
    when (response.code()) {
        499 -> throw NoNetworkException("No hay red")
        598 -> throw SocketTimeoutException("Finalizado tiempo espera")
        599 -> throw ConnectException("Error al conectar")
    }
}
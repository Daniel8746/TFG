package com.pmdm.casino.data.services.interceptors.connectVerifier

import com.pmdm.casino.data.exceptions.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectVerifierInterceptor @Inject constructor(
    private val networkMonitor: NetworkMonitorService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (networkMonitor.isConnected()) {
            return chain.proceed(request)
        }

        throw NoNetworkException("Network Error")
    }
}
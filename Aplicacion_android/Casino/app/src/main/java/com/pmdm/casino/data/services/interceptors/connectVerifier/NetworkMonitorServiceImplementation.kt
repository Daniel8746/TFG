package com.pmdm.casino.data.services.interceptors.connectVerifier

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkMonitorServiceImplementation @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitorService {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun isConnected(): Boolean {
        return connectivityManager.activeNetwork != null
    }
}
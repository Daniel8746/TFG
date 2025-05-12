package com.pmdm.casino.data.services.interceptors.connectVerifier

interface NetworkMonitorService {
    fun isConnected(): Boolean
}
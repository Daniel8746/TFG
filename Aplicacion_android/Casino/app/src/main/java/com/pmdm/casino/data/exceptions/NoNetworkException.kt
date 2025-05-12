package com.pmdm.casino.data.exceptions

import androidx.datastore.core.IOException

class NoNetworkException(mensaje: String) : IOException(mensaje)
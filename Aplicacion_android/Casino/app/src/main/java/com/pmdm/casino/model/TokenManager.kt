package com.pmdm.casino.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Crea un delegado de extensión para acceder al DataStore en el contexto de la aplicación
val Context.dataStore by preferencesDataStore(name = "user_prefs")

// TokenManager maneja el almacenamiento y recuperación del token JWT en el DataStore
object TokenManager {
    // Clave para almacenar y recuperar el token JWT en el DataStore
    private val JWT_KEY = stringPreferencesKey("jwt_token")

    // Función para guardar el token
    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[JWT_KEY] = token
        }
    }

    // Función para obtener el token
    suspend fun getToken(context: Context): String? {
        val preferences = context.dataStore.data.first()
        return preferences[JWT_KEY]
    }

    // Función para borrar el token
    suspend fun clearToken(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.remove(JWT_KEY)
        }
    }
}
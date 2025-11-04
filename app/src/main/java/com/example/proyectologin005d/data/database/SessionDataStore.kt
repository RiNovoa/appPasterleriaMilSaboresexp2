package com.example.proyectologin005d.data.database

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// DataStore asociado al Context
private val Context.dataStore by preferencesDataStore(name = "session_data")

class SessionDataStore(private val context: Context) {

    private val KEY_USER = stringPreferencesKey("current_user")

    /** Flujo reactivo del usuario logueado (nullable) */
    val currentUser: Flow<String?> = context.dataStore.data.map { it[KEY_USER] }

    /** Lectura puntual (para checks en LaunchedEffect) */
    suspend fun getUserOnce(): String? = context.dataStore.data.first()[KEY_USER]

    /** Guardar sesión */
    suspend fun saveUser(correo: String) {
        context.dataStore.edit { it[KEY_USER] = correo }
    }

    /** Borrar sesión */
    suspend fun clearUser() {
        context.dataStore.edit { it.remove(KEY_USER) }
    }
}

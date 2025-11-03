package com.example.proyectologin005d.data.database

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_session")

class SessionDataStore(private val context: Context) {
    companion object { private val KEY_USER = stringPreferencesKey("logged_user") }

    val currentUser: Flow<String?> = context.dataStore.data.map { it[KEY_USER] }

    suspend fun saveUser(username: String) {
        context.dataStore.edit { prefs -> prefs[KEY_USER] = username }
    }

    suspend fun clearUser() {
        context.dataStore.edit { prefs -> prefs.remove(KEY_USER) }
    }
}

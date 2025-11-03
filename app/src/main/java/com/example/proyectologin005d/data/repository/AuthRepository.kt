package com.example.proyectologin005d.data.repository

import android.content.Context
import com.example.proyectologin005d.data.database.ProductoDataBase
import com.example.proyectologin005d.data.database.SessionDataStore
import com.example.proyectologin005d.data.model.User
import kotlinx.coroutines.flow.first

class AuthRepository(context: Context) {

    private val db = ProductoDataBase.getDatabase(context)
    private val userDao = db.userDao()
    private val session = SessionDataStore(context)

    /** Registra un usuario nuevo. Devuelve true si se registró, false si el username existe. */
    suspend fun register(user: User): Boolean {
        val exists = userDao.findByUsername(user.username)
        if (exists != null) return false
        userDao.insertUser(user)
        // Auto-login tras registrar
        session.saveUser(user.username)
        return true
    }

    /** Inicia sesión. Devuelve true si las credenciales son válidas. */
    suspend fun login(username: String, password: String): Boolean {
        val u = userDao.login(username, password) ?: return false
        session.saveUser(u.username)
        return true
    }

    /** Obtiene el usuario en sesión (username) o null si no hay sesión. */
    suspend fun getSessionUsername(): String? = session.currentUser.first()

    /** Limpia la sesión local. */
    suspend fun logout() = session.clearUser()
}

package com.example.proyectologin005d.data.repository

import android.content.Context
import com.example.proyectologin005d.data.database.SessionDataStore
import com.example.proyectologin005d.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class AuthRepository(private val context: Context) {

    private val session = SessionDataStore(context)

    private val usersFile by lazy {
        val dir = File(context.filesDir, "database")
        if (!dir.exists()) dir.mkdirs()
        File(dir, "Usuarios.json")
    }

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    init {
        // Inicializa el archivo de usuarios desde assets (si existe), o crea vacío.
        runCatching {
            if (!usersFile.exists()) {
                val ok = runCatching {
                    context.assets.open("database/Usuarios.json").use { input ->
                        usersFile.outputStream().use { output -> input.copyTo(output) }
                    }
                }.isSuccess
                if (!ok) usersFile.writeText("[]")
            }
        }
    }

    private suspend fun readUsers(): MutableList<User> = withContext(Dispatchers.IO) {
        val text = runCatching { usersFile.readText() }.getOrElse { "[]" }
        runCatching { json.decodeFromString<MutableList<User>>(text) }.getOrElse { mutableListOf() }
    }

    private suspend fun writeUsers(users: List<User>) = withContext(Dispatchers.IO) {
        usersFile.writeText(json.encodeToString(users))
    }

    /** Registro simple en archivo local */
    suspend fun register(nombre: String, apellido: String, correo: String, contrasena: String): Boolean {
        return withContext(Dispatchers.IO) {
            val users = readUsers()
            if (users.any { it.correo.equals(correo, ignoreCase = true) }) return@withContext false
            val newId = (users.maxOfOrNull { it.id } ?: 0) + 1
            val newUser = User(id = newId, nombre = nombre, apellido = apellido, correo = correo, contrasena = contrasena, role = "user")
            users.add(newUser)
            writeUsers(users)
            session.saveUser(newUser.correo)
            true
        }
    }

    /** Login trivial contra el mismo archivo */
    suspend fun login(correo: String, contrasena: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = readUsers().find { it.correo.equals(correo, true) && it.contrasena == contrasena }
            if (user != null) {
                session.saveUser(user.correo)
                true
            } else false
        }
    }

    /** Para el autologin del inicio */
    suspend fun getSessionUsername(): String? = session.currentUser.first()

    fun getUserByUsername(correo: String): User? = runCatching {
        // Lectura no-suspend para usos puntuales UI; si quieres, cámbialo por suspend
        val text = usersFile.takeIf { it.exists() }?.readText().orEmpty()
        json.decodeFromString<List<User>>(text).find { it.correo.equals(correo, true) }
    }.getOrNull()

    suspend fun logout() = session.clearUser()
}

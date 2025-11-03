package com.example.proyectologin005d.ui.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectologin005d.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = AuthRepository(app.applicationContext)

    private val _ui = MutableStateFlow(LoginUiState())
    val ui: StateFlow<LoginUiState> = _ui

    // Inicializa SharedPreferences
    private val prefs: SharedPreferences =
        app.applicationContext.getSharedPreferences("user_data", Context.MODE_PRIVATE)

    // Funciones para cambiar valores en el UI
    fun onUsernameChange(v: String) { _ui.value = _ui.value.copy(username = v) }
    fun onPasswordChange(v: String) { _ui.value = _ui.value.copy(password = v) }

    /** Intenta login y, si ok, llama onSuccess(username). */
    fun submit(onSuccess: (String) -> Unit) {
        val s = _ui.value
        viewModelScope.launch {
            _ui.value = s.copy(isLoading = true, error = null)
            val ok = repo.login(s.username.trim(), s.password)
            if (ok) {
                // Guardar datos en SharedPreferences al loguearse
                saveUserData(s.username.trim(), "user@example.com") // Aqui deberías pasar el email real

                _ui.value = _ui.value.copy(isLoading = false, isLogged = true)
                onSuccess(s.username.trim())
            } else {
                _ui.value = _ui.value.copy(isLoading = false, error = "Usuario o contraseña inválidos")
            }
        }
    }

    /** Si hay sesión guardada, navega directo. */
    fun checkSession(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val u = repo.getSessionUsername()
            if (!u.isNullOrBlank()) {
                _ui.value = _ui.value.copy(isLogged = true, username = u)
                onSuccess(u)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
            _ui.value = LoginUiState()

            // Limpiar datos de SharedPreferences al hacer logout
            prefs.edit().clear().apply()
        }
    }

    // Obtener datos del usuario desde SharedPreferences
    fun getLoggedUserName(): String? = prefs.getString("username", null)
    fun getLoggedUserEmail(): String? = prefs.getString("email", null)

    // Guardar datos del usuario (nombre, correo) en SharedPreferences
    private fun saveUserData(username: String, email: String) {
        prefs.edit().apply {
            putString("username", username)
            putString("email", email)
            apply()
        }
    }
}

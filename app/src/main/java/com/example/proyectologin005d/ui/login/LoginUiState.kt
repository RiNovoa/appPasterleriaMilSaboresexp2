package com.example.proyectologin005d.ui.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLogged: Boolean = false
)

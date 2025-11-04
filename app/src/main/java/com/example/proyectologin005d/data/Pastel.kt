package com.example.proyectologin005d.data

import kotlinx.serialization.Serializable

@Serializable
data class Pastel(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val stock: Int,
    val imagen: String,
    val categoria: String,
    val descripcion: String
)

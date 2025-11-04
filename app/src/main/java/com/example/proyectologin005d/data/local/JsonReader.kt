package com.example.proyectologin005d.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

data class CatalogoProductoJson(
    val id: Int,
    val nombre: String,
    val precio: Int,
    val stock: Int,
    val imagen: String?,
    val categoria: String?,
    val descripcion: String?
)

object JsonReader {
    fun cargarCatalogo(
        context: Context,
        file: String = "database/Pasteles.json"
    ): List<CatalogoProductoJson> {
        context.assets.open(file).use { input ->
            InputStreamReader(input).use { reader ->
                val type = object : TypeToken<List<CatalogoProductoJson>>() {}.type
                return Gson().fromJson(reader, type)
            }
        }
    }
}

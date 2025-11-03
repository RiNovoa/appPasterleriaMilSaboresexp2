package com.example.proyectologin005d.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectologin005d.data.dao.ProductoDao
import com.example.proyectologin005d.data.dao.UserDao
import com.example.proyectologin005d.data.model.Producto
import com.example.proyectologin005d.data.model.User

@Database(
    entities = [Producto::class, User::class],
    version = 2,                      // subimos versión para incluir la tabla users
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: ProductoDataBase? = null

        fun getDatabase(context: Context): ProductoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDataBase::class.java,
                    "producto_database"
                )
                    // En desarrollo es lo más simple: borra y recrea si cambian entidades/version
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

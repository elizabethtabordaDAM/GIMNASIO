package com.example.gimnasio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoritoEntity::class,
        ProgresoRetoEntity::class,
        RegistroEntrenamientoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GimnasioDatabase : RoomDatabase() {

    abstract fun favoritoDao(): FavoritoDao
    abstract fun progresoRetoDao(): ProgresoRetoDao
    abstract fun registroDao(): RegistroEntrenamientoDao

    companion object {
        @Volatile
        private var INSTANCIA: GimnasioDatabase? = null

        // Devuelve siempre la misma base de datos (solo se crea una vez)
        fun obtener(context: Context): GimnasioDatabase {
            return INSTANCIA ?: synchronized(this) {
                INSTANCIA ?: Room.databaseBuilder(
                    context.applicationContext,
                    GimnasioDatabase::class.java,
                    "gimnasio.db"
                ).build().also { INSTANCIA = it }
            }
        }
    }
}
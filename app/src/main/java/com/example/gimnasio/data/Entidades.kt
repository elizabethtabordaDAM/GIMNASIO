package com.example.gimnasio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Rutinas favoritas: guarda el índice del objetivo en la lista "objetivos"
@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey val indiceObjetivo: Int,
    val agregadoEn: Long = System.currentTimeMillis()   // para mantener el orden en que se marcaron
)
// Progreso de cada Reto Flash: guarda cuántos días lleva
@Entity(tableName = "progreso_retos")
data class ProgresoRetoEntity(
    @PrimaryKey val indiceReto: Int,
    val diasCompletados: Int = 0
)

// Diario de entrenamiento (tabla nueva, la que tendrá el CRUD completo)
@Entity(tableName = "registros_entrenamiento")
data class RegistroEntrenamientoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val indiceObjetivo: Int,        // a cuál rutina/objetivo corresponde
    val fecha: String,              // ejemplo: "12/11/2026"
    val duracionMinutos: Int,
    val nota: String = "",
    val completado: Boolean = false
)
package com.example.gimnasio.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// ---------- Favoritos ----------
@Dao
interface FavoritoDao {

    @Query("SELECT * FROM favoritos ORDER BY agregadoEn ASC")
    fun observarTodos(): Flow<List<FavoritoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(favorito: FavoritoEntity)

    @Query("DELETE FROM favoritos WHERE indiceObjetivo = :indice")
    suspend fun eliminar(indice: Int)
}

// ---------- Progreso de los Retos Flash ----------
@Dao
interface ProgresoRetoDao {

    @Query("SELECT * FROM progreso_retos")
    fun observarTodos(): Flow<List<ProgresoRetoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardar(progreso: ProgresoRetoEntity)
}

// ---------- Diario de entrenamiento (CRUD completo) ----------
@Dao
interface RegistroEntrenamientoDao {

    // Consultar todos, el más nuevo primero
    @Query("SELECT * FROM registros_entrenamiento ORDER BY id DESC")
    fun observarTodos(): Flow<List<RegistroEntrenamientoEntity>>

    // Consultar uno solo (para editarlo)
    @Query("SELECT * FROM registros_entrenamiento WHERE id = :id")
    suspend fun obtenerPorId(id: Int): RegistroEntrenamientoEntity?

    // Crear
    @Insert
    suspend fun insertar(registro: RegistroEntrenamientoEntity)

    // Actualizar
    @Update
    suspend fun actualizar(registro: RegistroEntrenamientoEntity)

    // Eliminar
    @Delete
    suspend fun eliminar(registro: RegistroEntrenamientoEntity)
}
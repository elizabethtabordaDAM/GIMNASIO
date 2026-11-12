package com.example.gimnasio.data

import kotlinx.coroutines.flow.Flow

class GimnasioRepository(
    private val favoritoDao: FavoritoDao,
    private val progresoRetoDao: ProgresoRetoDao,
    private val registroDao: RegistroEntrenamientoDao
) {

    // ---------- Datos que la app observa (Flow) ----------
    val favoritos: Flow<List<FavoritoEntity>> = favoritoDao.observarTodos()
    val progresoRetos: Flow<List<ProgresoRetoEntity>> = progresoRetoDao.observarTodos()
    val registros: Flow<List<RegistroEntrenamientoEntity>> = registroDao.observarTodos()

    // ---------- Favoritos ----------
    suspend fun agregarFavorito(indice: Int) {
        favoritoDao.insertar(FavoritoEntity(indiceObjetivo = indice))
    }

    suspend fun quitarFavorito(indice: Int) {
        favoritoDao.eliminar(indice)
    }

    // ---------- Retos Flash ----------
    suspend fun guardarProgresoReto(indice: Int, dias: Int) {
        progresoRetoDao.guardar(ProgresoRetoEntity(indiceReto = indice, diasCompletados = dias))
    }

    // ---------- Diario de entrenamiento (CRUD) ----------
    suspend fun obtenerRegistro(id: Int): RegistroEntrenamientoEntity? {
        return registroDao.obtenerPorId(id)
    }

    suspend fun insertarRegistro(registro: RegistroEntrenamientoEntity) {
        registroDao.insertar(registro)
    }

    suspend fun actualizarRegistro(registro: RegistroEntrenamientoEntity) {
        registroDao.actualizar(registro)
    }

    suspend fun eliminarRegistro(registro: RegistroEntrenamientoEntity) {
        registroDao.eliminar(registro)
    }
}
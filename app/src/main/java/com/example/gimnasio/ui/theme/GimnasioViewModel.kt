package com.example.gimnasio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gimnasio.data.GimnasioDatabase
import com.example.gimnasio.data.GimnasioRepository
import com.example.gimnasio.data.RegistroEntrenamientoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Números que se muestran en la parte de estadísticas del Diario
data class EstadisticasDiario(
    val totalSesiones: Int = 0,
    val completadas: Int = 0,
    val totalMinutos: Int = 0
)

class GimnasioViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio: GimnasioRepository

    init {
        val bd = GimnasioDatabase.obtener(application)
        repositorio = GimnasioRepository(
            bd.favoritoDao(),
            bd.progresoRetoDao(),
            bd.registroDao()
        )
    }

    // ---------- Favoritos (lista de índices, igual que antes) ----------
    val favoritos: StateFlow<List<Int>> = repositorio.favoritos
        .map { lista -> lista.map { it.indiceObjetivo } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun alternarFavorito(indice: Int) {
        viewModelScope.launch {
            if (favoritos.value.contains(indice)) {
                repositorio.quitarFavorito(indice)
            } else {
                repositorio.agregarFavorito(indice)
            }
        }
    }

    // ---------- Retos Flash (un contador por reto, igual que antes) ----------
    val progresoRetos: StateFlow<List<Int>> = repositorio.progresoRetos
        .map { lista ->
            List(retosFlash.size) { i ->
                lista.find { it.indiceReto == i }?.diasCompletados ?: 0
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            List(retosFlash.size) { 0 }
        )

    fun completarDiaReto(indice: Int) {
        viewModelScope.launch {
            repositorio.guardarProgresoReto(indice, progresoRetos.value[indice] + 1)
        }
    }

    fun reiniciarReto(indice: Int) {
        viewModelScope.launch {
            repositorio.guardarProgresoReto(indice, 0)
        }
    }

    // ---------- Diario de entrenamiento ----------
    // Todos los registros guardados (se usa para buscar el que se va a editar)
    val registros: StateFlow<List<RegistroEntrenamientoEntity>> = repositorio.registros
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtro por objetivo: -1 significa "todos"
    private val _filtroObjetivo = MutableStateFlow(-1)
    val filtroObjetivo: StateFlow<Int> = _filtroObjetivo

    fun cambiarFiltro(indiceObjetivo: Int) {
        _filtroObjetivo.value = indiceObjetivo
    }

    // Registros ya filtrados
    val registrosFiltrados: StateFlow<List<RegistroEntrenamientoEntity>> =
        combine(repositorio.registros, _filtroObjetivo) { lista, filtro ->
            if (filtro == -1) lista else lista.filter { it.indiceObjetivo == filtro }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Estadísticas de lo que se está viendo
    val estadisticas: StateFlow<EstadisticasDiario> = registrosFiltrados
        .map { lista ->
            EstadisticasDiario(
                totalSesiones = lista.size,
                completadas = lista.count { it.completado },
                totalMinutos = lista.sumOf { it.duracionMinutos }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EstadisticasDiario())

    fun guardarRegistro(registro: RegistroEntrenamientoEntity) {
        viewModelScope.launch { repositorio.insertarRegistro(registro) }
    }

    fun actualizarRegistro(registro: RegistroEntrenamientoEntity) {
        viewModelScope.launch { repositorio.actualizarRegistro(registro) }
    }

    fun eliminarRegistro(registro: RegistroEntrenamientoEntity) {
        viewModelScope.launch { repositorio.eliminarRegistro(registro) }
    }

    fun alternarCompletado(registro: RegistroEntrenamientoEntity) {
        viewModelScope.launch {
            repositorio.actualizarRegistro(registro.copy(completado = !registro.completado))
        }
    }
}
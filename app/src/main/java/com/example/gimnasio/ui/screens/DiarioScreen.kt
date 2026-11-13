package com.example.gimnasio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gimnasio.data.RegistroEntrenamientoEntity
import com.example.gimnasio.data.objetivos
import com.example.gimnasio.ui.components.ChipObjetivo
import com.example.gimnasio.ui.components.EstadisticasDiarioCard
import com.example.gimnasio.ui.components.RegistroItem
import com.example.gimnasio.ui.components.TituloSeccion
import com.example.gimnasio.viewmodel.EstadisticasDiario

// =====================================================
//  PANTALLA 1: DIARIO (historial)
// =====================================================

@Composable
fun DiarioScreen(
    registros: List<RegistroEntrenamientoEntity>,
    estadisticas: EstadisticasDiario,
    filtroObjetivo: Int,
    onFiltroChange: (Int) -> Unit,
    onNuevo: () -> Unit,
    onEditar: (Int) -> Unit,
    onEliminar: (RegistroEntrenamientoEntity) -> Unit,
    onAlternarCompletado: (RegistroEntrenamientoEntity) -> Unit,
    onVolver: () -> Unit
) {
    // Guarda cuál registro se quiere eliminar (para pedir confirmación)
    var registroAEliminar by remember { mutableStateOf<RegistroEntrenamientoEntity?>(null) }

    registroAEliminar?.let { registro ->
        AlertDialog(
            onDismissRequest = { registroAEliminar = null },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("Eliminar registro") },
            text = { Text("¿Seguro que quieres eliminar este entrenamiento? No se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    onEliminar(registro)
                    registroAEliminar = null
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { registroAEliminar = null }) { Text("Cancelar") }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TituloSeccion(
                "Mi diario de entrenamiento 📝",
                "Registra lo que entrenaste y mira tu avance"
            )
        }

        item { EstadisticasDiarioCard(estadisticas) }

        // Filtro por objetivo
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    ChipObjetivo("Todos", filtroObjetivo == -1) { onFiltroChange(-1) }
                }
                itemsIndexed(objetivos) { indice, objetivo ->
                    ChipObjetivo(objetivo.nombre, filtroObjetivo == indice) {
                        onFiltroChange(indice)
                    }
                }
            }
        }

        item {
            Button(
                onClick = onNuevo,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("➕ Registrar entrenamiento")
            }
        }

        if (registros.isEmpty()) {
            item {
                Text(
                    text = "Aún no hay entrenamientos aquí. Toca \"Registrar entrenamiento\" para agregar el primero.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(registros, key = { it.id }) { registro ->
                RegistroItem(
                    registro = registro,
                    onEditar = { onEditar(registro.id) },
                    onEliminar = { registroAEliminar = registro },
                    onAlternarCompletado = { onAlternarCompletado(registro) }
                )
            }
        }

        item {
            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

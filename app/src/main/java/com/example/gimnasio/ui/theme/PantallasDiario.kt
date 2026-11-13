package com.example.gimnasio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gimnasio.data.RegistroEntrenamientoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// =====================================================
//  COMPONENTES REUTILIZABLES
// =====================================================

// Botoncito de selección (se usa en el filtro y en el formulario)
@Composable
fun ChipObjetivo(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun DatoEstadistica(valor: String, etiqueta: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun EstadisticasDiarioCard(estadisticas: EstadisticasDiario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DatoEstadistica("${estadisticas.totalSesiones}", "Sesiones")
            DatoEstadistica("${estadisticas.completadas}", "Completadas")
            DatoEstadistica("${estadisticas.totalMinutos}", "Minutos")
        }
    }
}

@Composable
fun RegistroItem(
    registro: RegistroEntrenamientoEntity,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    onAlternarCompletado: () -> Unit
) {
    val nombreObjetivo = objetivos.getOrNull(registro.indiceObjetivo)?.nombre ?: "Entrenamiento"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = nombreObjetivo, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${registro.fecha} · ${registro.duracionMinutos} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = if (registro.completado) "✅ Completado" else "⏳ Pendiente",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (registro.completado) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.tertiary
                )
            }

            if (registro.nota.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = registro.nota, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onAlternarCompletado) {
                    Text(if (registro.completado) "Pendiente" else "Completar")
                }
                TextButton(onClick = onEditar) { Text("Editar") }
                TextButton(
                    onClick = onEliminar,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Eliminar") }
            }
        }
    }
}

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

// =====================================================
//  PANTALLA 2: FORMULARIO (registrar y editar)
// =====================================================
@Composable
fun FormularioRegistroScreen(
    registroInicial: RegistroEntrenamientoEntity?,   // null = registro nuevo
    onGuardar: (RegistroEntrenamientoEntity) -> Unit,
    onVolver: () -> Unit
) {
    val fechaHoy = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
    }

    var indiceObjetivo by remember { mutableStateOf(registroInicial?.indiceObjetivo ?: -1) }
    var fecha by remember { mutableStateOf(registroInicial?.fecha ?: fechaHoy) }
    var duracion by remember { mutableStateOf(registroInicial?.duracionMinutos?.toString() ?: "") }
    var nota by remember { mutableStateOf(registroInicial?.nota ?: "") }
    var intentoGuardar by remember { mutableStateOf(false) }

    // Validaciones básicas
    val duracionNumero = duracion.toIntOrNull()
    val errorObjetivo = indiceObjetivo == -1
    val errorFecha = fecha.isBlank()
    val errorDuracion = duracionNumero == null || duracionNumero !in 1..300

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TituloSeccion(
            if (registroInicial == null) "Registrar entrenamiento" else "Editar entrenamiento",
            "Completa los datos de tu sesión"
        )

        Text("¿Qué entrenaste?", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(objetivos) { indice, objetivo ->
                ChipObjetivo(objetivo.nombre, indiceObjetivo == indice) {
                    indiceObjetivo = indice
                }
            }
        }
        if (intentoGuardar && errorObjetivo) {
            Text(
                "Elige un objetivo",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (dd/mm/aaaa)") },
            singleLine = true,
            isError = intentoGuardar && errorFecha,
            supportingText = {
                if (intentoGuardar && errorFecha) Text("La fecha no puede estar vacía")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = duracion,
            onValueChange = { nuevo -> duracion = nuevo.filter { it.isDigit() }.take(3) },
            label = { Text("Duración (minutos)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = intentoGuardar && errorDuracion,
            supportingText = {
                if (intentoGuardar && errorDuracion) Text("Escribe un número entre 1 y 300")
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nota,
            onValueChange = { nota = it },
            label = { Text("Nota (opcional)") },
            minLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                intentoGuardar = true
                if (!errorObjetivo && !errorFecha && !errorDuracion) {
                    onGuardar(
                        RegistroEntrenamientoEntity(
                            id = registroInicial?.id ?: 0,
                            indiceObjetivo = indiceObjetivo,
                            fecha = fecha.trim(),
                            duracionMinutos = duracionNumero ?: 0,
                            nota = nota.trim(),
                            completado = registroInicial?.completado ?: false
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(if (registroInicial == null) "Guardar registro" else "Guardar cambios")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
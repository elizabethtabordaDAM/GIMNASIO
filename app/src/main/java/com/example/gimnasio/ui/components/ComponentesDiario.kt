package com.example.gimnasio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gimnasio.data.RegistroEntrenamientoEntity
import com.example.gimnasio.data.objetivos
import com.example.gimnasio.viewmodel.EstadisticasDiario

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

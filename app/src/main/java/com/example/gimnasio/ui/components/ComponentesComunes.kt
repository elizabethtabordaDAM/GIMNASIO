package com.example.gimnasio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TituloSeccion(titulo: String, subtitulo: String? = null) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold
    )

    if (subtitulo != null) {
        Text(
            text = subtitulo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun BotonFavorito(
    esFavorito: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0x990F1115))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = if (esFavorito) "❤️" else "🤍", fontSize = 20.sp)
    }
}

@Composable
fun BloqueConsejo(texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f))
            .padding(12.dp)
    ) {
        Text(text = "💡", fontSize = 20.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = "Consejo",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = texto,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BloqueEjercicio(titulo: String, lineas: List<String>) {
    // Si la primera línea empieza con "Series" o "Duración", se muestra como etiqueta
    val hayEtiqueta = lineas.isNotEmpty() &&
            (lineas.first().startsWith("Series") || lineas.first().startsWith("Duración"))
    val etiqueta = if (hayEtiqueta) lineas.first() else null
    val descripcion = (if (hayEtiqueta) lineas.drop(1) else lineas).joinToString(" ")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        if (etiqueta != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        if (descripcion.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun InstruccionesDia(texto: String) {
    // Separa el texto en bloques usando las líneas en blanco
    val bloques = texto.trim().split("\n\n").filter { it.isNotBlank() }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        for (bloque in bloques) {
            val lineas = bloque.lines().map { it.trim() }.filter { it.isNotEmpty() }
            val titulo = lineas.first()
            val resto = lineas.drop(1)

            if (titulo.uppercase().startsWith("CONSEJO")) {
                BloqueConsejo(resto.joinToString(" "))
            } else {
                BloqueEjercicio(titulo, resto)
            }
        }
    }
}

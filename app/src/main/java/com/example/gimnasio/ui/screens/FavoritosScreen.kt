package com.example.gimnasio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gimnasio.data.objetivos
import com.example.gimnasio.ui.components.FavoritoItem
import com.example.gimnasio.ui.components.TituloSeccion

@Composable
fun FavoritosScreen(
    favoritos: List<Int>,
    onRutinaClick: (Int) -> Unit,
    onQuitarFavorito: (Int) -> Unit,
    onVolver: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TituloSeccion(
                "Mis rutinas favoritas ❤️",
                if (favoritos.isEmpty()) null
                else "${favoritos.size} ${if (favoritos.size == 1) "rutina guardada" else "rutinas guardadas"}"
            )
        }

        if (favoritos.isEmpty()) {
            item {
                Text(
                    text = "Aún no tienes favoritas. Toca el 🤍 en cualquier rutina para guardarla aquí.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(favoritos, key = { it }) { indice ->
                FavoritoItem(
                    objetivo = objetivos[indice],
                    onClick = { onRutinaClick(indice) },
                    onQuitar = { onQuitarFavorito(indice) }
                )
            }
        }

        item {
            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Volver")
            }
        }
    }
}

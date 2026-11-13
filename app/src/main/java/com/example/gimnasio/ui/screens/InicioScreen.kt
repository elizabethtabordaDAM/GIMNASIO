package com.example.gimnasio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gimnasio.data.actividades
import com.example.gimnasio.data.estadosEnergia
import com.example.gimnasio.data.objetivos
import com.example.gimnasio.data.retosFlash
import com.example.gimnasio.ui.components.ActividadCard
import com.example.gimnasio.ui.components.ObjetivoCard
import com.example.gimnasio.ui.components.PerfilHeroeCard
import com.example.gimnasio.ui.components.ResultadoBusquedaItem
import com.example.gimnasio.ui.components.RetoFlashCard
import com.example.gimnasio.ui.components.SemaforoItem
import com.example.gimnasio.ui.components.TituloSeccion
import com.example.gimnasio.ui.theme.GIMNASIOTheme
import com.example.gimnasio.util.buscarObjetivos

@Composable
fun GIMNASIO(
    onObjetivoClick: (Int) -> Unit = {},
    onActividadClick: (Int) -> Unit = {},
    onRetoClick: (Int) -> Unit = {},
    progresoRetos: List<Int> = listOf(0, 0, 0),
    favoritos: List<Int> = emptyList(),
    onFavoritoClick: (Int) -> Unit = {},
    onVerFavoritos: () -> Unit = {},
    onVerDiario: () -> Unit = {}
) {
    // Scroll vertical de toda la pantalla (deslizar hacia arriba / abajo)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Gimnasio App",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Tu historia de transformación comienza aquí. Sube de nivel.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        var busqueda by remember { mutableStateOf("") }
        val resultados = buscarObjetivos(busqueda)

        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar rutinas (ej: glúteo, espalda, yoga)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // Al dar Enter, abre la primera rutina que coincide
                    if (resultados.isNotEmpty()) {
                        onObjetivoClick(resultados[0])
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Resultados en vivo, debajo del buscador
        if (busqueda.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))

            if (resultados.isEmpty()) {
                Text(
                    text = "No encontramos rutinas para \"$busqueda\". Prueba con glúteo, pecho o yoga.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                for (indice in resultados) {
                    ResultadoBusquedaItem(
                        objetivo = objetivos[indice],
                        onClick = { onObjetivoClick(indice) }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        PerfilHeroeCard(racha = progresoRetos.sum())

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onVerFavoritos,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("❤️ Mis rutinas favoritas (${favoritos.size})")
        }


        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onVerDiario,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("📝 Mi diario de entrenamiento")
        }

        // ----- Objetivos (deslizar a los lados y tocar para ver la rutina) -----
        TituloSeccion("¿Cuál es tu objetivo?", "Desliza para ver todos →")

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(objetivos) { indice, objetivo ->
                ObjetivoCard(
                    objetivo = objetivo,
                    esFavorito = favoritos.contains(indice),
                    onFavoritoClick = { onFavoritoClick(indice) },
                    onClick = { onObjetivoClick(indice) }
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))

        // ----- Muévete a tu ritmo -----
        TituloSeccion("¿Poco tiempo? ", "¡Muévete al ritmo que quieras! Elige tu opción ideal y entrena feliz.💃 →")

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(actividades) { indice, actividad ->
                ActividadCard(actividad, onClick = { onActividadClick(indice) })
            }
        }
        Spacer(modifier = Modifier.height(28.dp))

        // ----- Semáforo de Fatiga -----
        // ----- Semáforo de Fatiga -----
        TituloSeccion("¿Cómo va esa energía?", " Hoy entrenas para ti.Tu entrenamiento se adapta a cómo te sientes")

        var energiaSeleccionada by remember { mutableStateOf(-1) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            estadosEnergia.forEachIndexed { indice, estado ->
                SemaforoItem(
                    estado = estado,
                    seleccionado = energiaSeleccionada == indice,
                    onClick = { energiaSeleccionada = indice },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (energiaSeleccionada != -1) {
            val elegido = estadosEnergia[energiaSeleccionada]
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = elegido.color.copy(alpha = 0.18f),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = elegido.mensaje,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))

        // ----- Retos Flash -----
        TituloSeccion("Retos Flash ⚡", "¿Sin tiempo? Mantén tu racha con un micro-reto")

        for (indice in retosFlash.indices) {
            RetoFlashCard(
                reto = retosFlash[indice],
                diasCompletados = progresoRetos[indice],
                onClick = { onRetoClick(indice) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

    }


}
@Preview(showBackground = true)
@Composable
fun GIMNASIOPreview() {
    GIMNASIOTheme {
        Surface(color = MaterialTheme.colorScheme.background, contentColor = 	MaterialTheme.colorScheme.onSurface) {
            GIMNASIO()
        }
    }
}



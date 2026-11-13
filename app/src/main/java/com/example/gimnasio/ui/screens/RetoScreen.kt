package com.example.gimnasio.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gimnasio.model.RetoFlash
import com.example.gimnasio.ui.components.DiaRetoCard
import com.example.gimnasio.ui.components.Redondeado


@Composable
fun RetoScreen(
    reto: RetoFlash,
    racha: Int,
    diasCompletados: Int,
    onCompletarDia: () -> Unit,
    onReiniciar: () -> Unit,
    onVolver: () -> Unit
) {
    val total = reto.dias.size

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ----- Cabecera con imagen -----
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(Redondeado)
            ) {
                Image(
                    painter = painterResource(id = reto.imagen),
                    contentDescription = reto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color(0xDD0F1115))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = reto.nombre,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Reto de $total días · ${reto.duracion} al día",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // ----- Progreso del reto -----

        item {
            Column {
                Text(
                    text = "🔥 Racha: $racha ${if (racha == 1) "día" else "días"}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (diasCompletados == total) "¡Reto completado! 🏆"
                    else "Llevas $diasCompletados de $total días",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { diasCompletados / total.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )
            }
        }


        // ----- Un card por cada día -----
        itemsIndexed(reto.dias) { indice, dia ->
            DiaRetoCard(
                numero = indice + 1,
                dia = dia,
                completado = indice < diasCompletados,
                esSiguiente = indice == diasCompletados,
                onCompletar = onCompletarDia
            )
        }

        // ----- Reiniciar (solo si ya avanzó) -----
        if (diasCompletados > 0) {
            item {
                Button(
                    onClick = onReiniciar,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Text("Reiniciar reto")
                }
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


package com.example.gimnasio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gimnasio.data.RegistroEntrenamientoEntity
import com.example.gimnasio.data.objetivos
import com.example.gimnasio.ui.components.ChipObjetivo
import com.example.gimnasio.ui.components.TituloSeccion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


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

        Text("¿Qué Deseas Entrenar el Día de Hoy?", style = MaterialTheme.typography.titleMedium)
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
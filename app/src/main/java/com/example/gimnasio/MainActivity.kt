package com.example.gimnasio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gimnasio.ui.theme.GIMNASIOTheme

// ---------- Paleta elegante: carbón + champagne + esmeralda + coral ----------
val Carbon = Color(0xFF0F1115)
val Tarjeta = Color(0xFF181B22)
val TarjetaClara = Color(0xFF222630)
val Champagne = Color(0xFFD4B483)
val Esmeralda = Color(0xFF2F8F83)
val Coral = Color(0xFFFF7A59)
val TextoSuave = Color(0xFFA7ADBA)
val Verde = Color(0xFF3DB88A)
val Ambar = Color(0xFFE0A93B)
val Rojo = Color(0xFFE0594F)
val Redondeado = RoundedCornerShape(20.dp)

// ---------- Modelos de datos ----------
data class DiaRutina(
    val dia: String,
    val enfoque: String,
    val ejercicios: String,
    val duracion: String
)

// Cada objetivo trae dentro su rutina semanal
data class Objetivo(
    @DrawableRes val imagen: Int,
    val nombre: String,
    val descripcion: String,
    val mensaje: String,
    val dias: List<DiaRutina>
)

data class Actividad(
    @DrawableRes val imagen: Int,
    val nombre: String,
    val mensaje: String,
    val duracion: String
)

data class Capitulo(
    @DrawableRes val imagen: Int,
    val titulo: String,
    val descripcion: String,
    val xp: String,
    val desbloqueado: Boolean
)

data class RetoFlash(
    @DrawableRes val imagen: Int,
    val nombre: String,
    val duracion: String
)

data class Logro(
    @DrawableRes val imagen: Int,
    val titulo: String,
    val descripcion: String
)

data class EstadoEnergia(
    val emoji: String,
    val nombre: String,
    val mision: String,
    val color: Color
)

// ---------- Colecciones ----------

val objetivos = listOf(
    Objetivo(
        R.drawable.gluetoypierna, "Glúteos y Piernas", " Dar Volumen y forma",
        "Si tu objetivo es aumentar glúteos, esta es tu rutina de la semana",
        listOf(
            DiaRutina("Lunes", "Glúteo y pierna", "Hip thrust · Sentadilla búlgara · Peso muerto rumano", "50 min"),
            DiaRutina("Martes", "Tren superior ligero", "Remo · Press de hombro · Plancha", "35 min"),
            DiaRutina("Miércoles", "Descanso activo", "Caminata y movilidad de cadera", "20 min"),
            DiaRutina("Jueves", "Glúteo e isquios", "Puente de glúteo · Patada en polea · Zancadas", "50 min"),
            DiaRutina("Viernes", "Pierna completa", "Sentadilla · Prensa · Abducciones", "55 min"),
            DiaRutina("Sábado", "Cardio suave", "Escaladora · Abdomen", "30 min"),
            DiaRutina("Domingo", "Descanso", "Tu cuerpo también entrena descansando", "—")
        )
    ),
    Objetivo(
        R.drawable.tonificar, "Tonificar", "Define y quema grasa sin perder musculo",
        "Si tu objetivo es tonificar, esta es tu rutina para toda la semana",
        listOf(
            DiaRutina("Lunes", "Cuerpo completo", "Circuito de 6 estaciones con mancuernas", "40 min"),
            DiaRutina("Martes", "Cardio + core", "Intervalos · Plancha · Bicicleta abdominal", "35 min"),
            DiaRutina("Miércoles", "Tren inferior", "Sentadilla goblet · Zancadas · Puente", "40 min"),
            DiaRutina("Jueves", "Yoga y movilidad", "Flujo suave y estiramientos", "30 min"),
            DiaRutina("Viernes", "Tren superior", "Press · Remo · Curl · Tríceps", "40 min"),
            DiaRutina("Sábado", "Funcional", "Kettlebell · Saltos · Core", "35 min"),
            DiaRutina("Domingo", "Descanso", "Recarga energía para la próxima semana", "—")
        )
    ),
    Objetivo(
        R.drawable.cuerpo_completo, "Cuerpo completo", "Mejorar Resistencia y Condición Fisica",
        "Si tu objetivo es trabajar todo el cuerpo, esta es tu rutina semanal",
        listOf(
            DiaRutina("Lunes", "Fuerza base", "Sentadilla · Press de banca · Remo", "55 min"),
            DiaRutina("Martes", "Cardio moderado", "Trote suave o bicicleta", "30 min"),
            DiaRutina("Miércoles", "Fuerza base", "Peso muerto · Press militar · Dominadas asistidas", "55 min"),
            DiaRutina("Jueves", "Movilidad", "Estiramientos y core", "25 min"),
            DiaRutina("Viernes", "Circuito total", "Burpees · Kettlebell · Cuerda", "40 min"),
            DiaRutina("Sábado", "Actividad libre", "Caminata, natación o deporte", "45 min"),
            DiaRutina("Domingo", "Descanso", "Dormir bien también suma XP", "—")
        )
    ),
    Objetivo(
        R.drawable.fuerza_potencia, "Fuerza", "Más potencia y mayor rendimiento",
        "Si tu objetivo es ganar fuerza, esta es tu rutina de la semana",
        listOf(
            DiaRutina("Lunes", "Empuje", "Press de banca · Press militar · Fondos", "55 min"),
            DiaRutina("Martes", "Tirón", "Dominadas · Remo con barra · Curl", "55 min"),
            DiaRutina("Miércoles", "Descanso activo", "Caminata y estiramientos", "20 min"),
            DiaRutina("Jueves", "Pierna", "Sentadilla · Peso muerto · Prensa", "60 min"),
            DiaRutina("Viernes", "Fuerza total", "Series pesadas de los básicos", "55 min"),
            DiaRutina("Sábado", "Movilidad", "Yoga y trabajo de core", "30 min"),
            DiaRutina("Domingo", "Descanso", "Los músculos crecen mientras descansas", "—")
        )
    ),
    Objetivo(
        R.drawable.tren_superior, "Enfoque en Tren Superior ", "Fortalecer y marcar tren superior",
        "Si tu objetivo es perder grasa, esta es tu rutina de la semana",
        listOf(
            DiaRutina("Lunes", "HIIT suave", "Intervalos 30/30 · Sentadillas con salto", "30 min"),
            DiaRutina("Martes", "Fuerza cuerpo completo", "Circuito con mancuernas", "45 min"),
            DiaRutina("Miércoles", "Cardio constante", "Trote o bicicleta en zona media", "40 min"),
            DiaRutina("Jueves", "Fuerza tren inferior", "Zancadas · Sentadilla · Puente", "45 min"),
            DiaRutina("Viernes", "Baile cardio", "Zumba o rumba", "45 min"),
            DiaRutina("Sábado", "Caminata larga", "Al aire libre y a tu ritmo", "60 min"),
            DiaRutina("Domingo", "Descanso", "Recuperar también es progresar", "—")
        )
    )
)

val actividades = listOf(
    Actividad(R.drawable.zumba, "Zumba", "Bailar también oxigena tu cerebro y levanta tu ánimo", "45 min"),
    Actividad(R.drawable.saliratrotar, "Salir a trotar", "Un trote al aire libre despeja la mente y cuida tu corazón", "30 min"),
    Actividad(R.drawable.relajacion, "Relajar el cuerpo", "Respira, estírate y suelta la tensión del día", "20 min"),
    Actividad(R.drawable.caminata_libre, "Caminata libre", "Caminar a tu ritmo también cuenta como entrenar", "40 min")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GIMNASIOTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Carbon,
                    contentColor = Color.White
                ) {
                    GIMNASIO()
                }
            }
        }
    }
}

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
            color = TextoSuave
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun GIMNASIO() {
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
            color = Champagne
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Buscar niveles o rutinas") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        PerfilHeroeCard()

        Spacer(modifier = Modifier.height(28.dp))

        // ----- Objetivos (deslizar a los lados y tocar para ver la rutina) -----
        TituloSeccion("¿Cuál es tu objetivo?", "Desliza para ver todos →")

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (objetivo in objetivos) {
                ObjetivoCard(objetivo)
            }
        }
        Spacer(modifier = Modifier.height(28.dp))

        // ----- Muévete a tu ritmo -----
        TituloSeccion("¿Poco tiempo? ", "¡Muévete al ritmo que quieras! Elige tu opción ideal y entrena feliz.💃 →")

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (actividad in actividades) {
                ActividadCard(actividad)
            }
        }

    }


}

@Composable
fun PerfilHeroeCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Redondeado)
            .background(Brush.horizontalGradient(listOf(Esmeralda, Tarjeta)))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.imagen_principal),
                    contentDescription = "Tu Guardián",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.size(12.dp))

                Column {
                    Text(
                        text = "Nivel 2 · Aprendiz de Hierro",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tu Guardián evoluciona con cada entrenamiento",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { 0.65f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = Champagne,
                trackColor = Color.White.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "650 / 1.000 XP", style = MaterialTheme.typography.bodySmall)
                Text(text = "🔥 Racha: 7 días", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun ObjetivoCard(objetivo: Objetivo) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .height(240.dp)
            .clip(Redondeado)
            .clickable { } // solo efecto al presionar, todavía no abre nada
    ) {
        Image(
            painter = painterResource(id = objetivo.imagen),
            contentDescription = objetivo.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Degradado oscuro para que el texto se lea bien sobre la foto
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
                .padding(14.dp)
        ) {
            Text(
                text = objetivo.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = objetivo.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Champagne
            )
        }
    }
}
@Composable
fun ActividadCard(actividad: Actividad) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .clip(Redondeado)
            .clickable { }, // solo efecto al presionar
        shape = Redondeado,
        colors = CardDefaults.cardColors(containerColor = Tarjeta, contentColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = actividad.imagen),
                contentDescription = actividad.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(14.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = actividad.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = actividad.duracion,
                    style = MaterialTheme.typography.labelMedium,
                    color = Coral
                )
            }

            Text(
                text = actividad.mensaje,
                style = MaterialTheme.typography.bodySmall,
                color = TextoSuave
            )
        }
    }
}
package com.example.gimnasio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.gimnasio.ui.theme.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import java.text.Normalizer


val Redondeado = RoundedCornerShape(20.dp)

// ---------- Modelos de datos ----------
data class Ejercicio(
    val nombre: String,
    val series: String,          // Ej: "4 series x 12 repeticiones"
    val descripcion: String,     // Cómo se hace (el "ejemplo")
    @DrawableRes val imagen: Int? = null
)

data class DiaRutina(
    val dia: String,
    val enfoque: String,
    val ejercicios: String,
    val duracion: String,
    val detalle: List<Ejercicio> = emptyList(),   // <-- NUEVO (va de 5to para no romper tus llamadas)
    @DrawableRes val imagen: Int? = null,
    val instrucciones: String = ""
)
// Cada objetivo trae dentro su rutina semanal
data class Objetivo(
    @DrawableRes val imagen: Int,
    val nombre: String,
    val descripcion: String,
    val mensaje: String,
    val dias: List<DiaRutina>
)

data class Fase(
    val nombre: String,
    val duracion: String,
    val descripcion: String
)

data class Actividad(
    @DrawableRes val imagen: Int,
    val nombre: String,
    val mensaje: String,
    val duracion: String,
    val fases: List<Fase>,
    val consejo: String
)

data class Capitulo(
    @DrawableRes val imagen: Int,
    val titulo: String,
    val descripcion: String,
    val xp: String,
    val desbloqueado: Boolean
)

data class DiaReto(
    val titulo: String,
    val descripcion: String
)

data class RetoFlash(
    @DrawableRes val imagen: Int,
    val nombre: String,
    val duracion: String,
    val dias: List<DiaReto>
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
    val color: Color,
    val mensaje: String
)

// ---------- Colecciones ----------

val objetivos = listOf(
    Objetivo(
        R.drawable.gluetoypierna, "Glúteos y Piernas", "Dar volumen y forma",
        "Si tu objetivo es aumentar glúteos, esta es tu rutina de la semana",
        listOf(
            DiaRutina(
                "Lunes", "Glúteo y pierna",
                "Hip thrust · Sentadilla búlgara · Peso muerto rumano", "50 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Hip thrust",
                        series = "4 series x 12 repeticiones sosteniendo 3 segundos al subir",
                        descripcion = "Apoya la espalda alta en un banco, barra sobre la cadera. Empuja con los talones hasta alinear rodillas, cadera y hombros. ",
                        imagen = R.drawable.puentedegluteo
                    ),
                    Ejercicio(
                        nombre = "Sentadilla búlgara",
                        series = "3 series x 10 repeticiones por pierna",
                        descripcion = "Pie trasero sobre un banco. Baja controlado hasta que el muslo delantero quede paralelo al piso y sube empujando con el talón.",
                        imagen = R.drawable.sentadillabulgara
                    ),
                    Ejercicio(
                        nombre = "Peso muerto rumano",
                        series = "4 series x 10 repeticiones",
                        descripcion = "Rodillas semiflexionadas, espalda recta. Lleva la cadera hacia atrás bajando la mancuerna pegada a las piernas y sube apretando glúteos.",
                        imagen = R.drawable.pesomuerto
                    )
                )
            ),
            DiaRutina(
                "Martes", "Tren superior ligero",
                "Remo · Press de hombro · Plancha", "35 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Remo con mancuerna",
                        series = "3 series x 12 repeticiones por lado",
                        descripcion = "Apoya una mano y una rodilla en el banco con la espalda recta. Jala la mancuerna hacia la cadera llevando el codo atrás y baja lento.",
                        imagen = R.drawable.remoconmancuerna
                    ),
                    Ejercicio(
                        nombre = "Press de hombro",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Mancuernas a la altura de las orejas. Empuja hacia arriba sin arquear la espalda y baja de forma controlada.",
                        imagen = R.drawable.pressdehombro
                    ),
                    Ejercicio(
                        nombre = "Plancha",
                        series = "3 series x 30 segundos",
                        descripcion = "Antebrazos en el piso y cuerpo en línea recta de cabeza a talones. Aprieta abdomen y glúteos sin dejar caer la cadera.",
                        imagen = R.drawable.plancha
                    )
                )
            ),
            DiaRutina(
                "Miércoles", "Descanso activo",
                "Caminata y movilidad de cadera", "20 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Caminata suave",
                        series = "10 minutos",
                        descripcion = "Camina a un ritmo cómodo, en el que puedas conversar. Activa la circulación y ayuda a recuperar los glúteos.",
                        imagen = R.drawable.caminata_libre
                    ),
                    Ejercicio(
                        nombre = "Círculos de cadera",
                        series = "2 series x 10 repeticiones por lado",
                        descripcion = "De pie, apoyado en una pared, levanta la rodilla y dibuja círculos amplios con la cadera.",
                        imagen = R.drawable.circulos
                    ),
                    Ejercicio(
                        nombre = "Postura de la paloma",
                        series = "2 series x 30 segundos por lado",
                        descripcion = "Lleva una pierna doblada al frente y estira la otra hacia atrás. Respira profundo y siente el estiramiento en el glúteo.",
                        imagen = R.drawable.posturadepaloma
                    )
                )
            ),
            DiaRutina(
                "Jueves", "Glúteo e isquios, Aumenta el peso acorde vayas avanzando",
                "Puente de glúteo · Patada en polea · Zancadas", "50 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Puente de glúteo",
                        series = "4 series x 15 repeticiones, sosteniendo 3 segundos al subir en cada repetición",
                        descripcion = "Acostado boca arriba con rodillas dobladas y pies firmes. Eleva la cadera apretando el glúteo arriba y baja lento.",
                        imagen = R.drawable.puentedegluteo
                    ),
                    Ejercicio(
                        nombre = "Patada de glúteo en polea",
                        series = "4 series x 15 repeticiones por pierna",
                        descripcion = "Tobillera en la polea baja y manos apoyadas en la máquina. Lleva la pierna hacia atrás sin arquear la zona lumbar.",
                        imagen = R.drawable.patadadegluteoenpolea
                    ),
                    Ejercicio(
                        nombre = "Zancadas",
                        series = "4 series x 12 repeticiones por pierna",
                        descripcion = "Da un paso largo al frente y baja hasta que ambas rodillas formen 90 grados. Empuja con el talón delantero para volver.",
                        imagen = R.drawable.zancadas
                    )
                )
            ),
            DiaRutina(
                "Viernes", "Pierna completa, aumenta el peso en las maquinas acorde vas subiendo de nivel",
                "Sentadilla · Prensa · Abducciones", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Sentadilla con barra",
                        series = "4 series x 10 repeticiones",
                        descripcion = "Pies al ancho de hombros y pecho arriba. Lleva la cadera atrás y abajo hasta que los muslos queden paralelos al piso.",
                        imagen = R.drawable.sentadillaconbarra
                    ),
                    Ejercicio(
                        nombre = "Prensa de pierna",
                        series = "4 series x 12 repeticiones ",
                        descripcion = "Pies al ancho de hombros en la plataforma. Baja hasta 90 grados en las rodillas y empuja sin bloquearlas al subir.",
                        imagen = R.drawable.prensadepierna
                    ),
                    Ejercicio(
                        nombre = "Abducciones en máquina",
                        series = "3 series x 15 repeticiones sosteniendo 3 segundos al abrir",
                        descripcion = "Sentado con la espalda pegada al respaldo. Abre las piernas contra la resistencia y regresa lento. Trabaja el glúteo medio.",
                        imagen = R.drawable.adupcionenmaquina
                    )
                )
            )
        )
    ),
    Objetivo(
        R.drawable.tonificar, "Tonificar", "Define y quema grasa sin perder músculo",
        "Si tu objetivo es tonificar, esta es tu rutina para toda la semana",
        listOf(
            DiaRutina(
                "Lunes", "Cuerpo completo",
                "Circuito de 6 estaciones con mancuernas", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Sentadilla goblet",
                        series = "40 seg de trabajo · 20 seg de pausa· 5 rondas ",
                        descripcion = "Sostén una mancuerna junto al pecho. Baja con la espalda recta hasta que los muslos queden paralelos al piso y sube.",
                        imagen = R.drawable.sentadillagoble
                    ),
                    Ejercicio(
                        nombre = "Remo con mancuerna",
                        series = "40 seg de trabajo · 20 seg de pausa· 5 a 7 rondas",
                        descripcion = "Inclina el torso con la espalda recta y jala las mancuernas hacia la cadera, juntando los omóplatos.",
                        imagen = R.drawable.remoconmancuerna
                    ),
                    Ejercicio(
                        nombre = "Press de hombro",
                        series = "40 seg de trabajo · 20 seg de pausa · 5 rondas",
                        descripcion = "Mancuernas a la altura de las orejas. Empuja hacia arriba y baja controlado sin arquear la espalda.",
                        imagen = R.drawable.pressdehombro
                    ),
                    Ejercicio(
                        nombre = "Peso muerto con mancuernas",
                        series = "40 seg de trabajo · 20 seg de pausa · 5 rondas",
                        descripcion = "Rodillas ligeramente flexionadas y espalda recta. Baja las mancuernas pegadas a las piernas y sube apretando glúteos.",
                        imagen = R.drawable.pesomuerto
                    ),
                    Ejercicio(
                        nombre = "Flexiones",
                        series = "40 seg de trabajo · 20 seg de pausa · 5  repeticiones",
                        descripcion = "Manos bajo los hombros y cuerpo en línea recta. Baja el pecho cerca del piso y empuja. Puedes apoyar las rodillas.",
                        imagen = R.drawable.flexiones
                    ),
                    Ejercicio(
                        nombre = "Plancha",
                        series = "40 seg de trabajo · 20 seg de pausa · 4 repeticiones",
                        descripcion = "Antebrazos en el piso y abdomen firme. Mantén el cuerpo recto sin subir ni hundir la cadera.",
                        imagen = R.drawable.plancha
                    )
                )
            ),
            DiaRutina(
                "Martes", "Cardio + core",
                "Intervalos · Plancha · Bicicleta abdominal", "35 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Intervalos de cardio",
                        series = "8 rondas: 30 seg rápido · 30 seg suave",
                        descripcion = "Salta cuerda alternando la intensidad. Es ideal para quemar grasa.",
                        imagen = R.drawable.saltandocuerda
                    ),
                    Ejercicio(
                        nombre = "Plancha",
                        series = "3 series x 40 segundos",
                        descripcion = "Antebrazos en el piso y cuerpo alineado. Aprieta abdomen y glúteos durante todo el tiempo.",
                        imagen = R.drawable.plancha
                    ),
                    Ejercicio(
                        nombre = "Bicicleta abdominal",
                        series = "3 series x 20 repeticiones",
                        descripcion = "Acostado boca arriba, lleva el codo hacia la rodilla contraria alternando lados, como si pedalearas.",
                        imagen = R.drawable.bicicletaabdominal
                    )
                )
            ),
            DiaRutina(
                "Miércoles", "Tren inferior",
                "Sentadilla goblet · Zancadas · Puente", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Sentadilla goblet",
                        series = "4 series x 12 a 15  repeticiones",
                        descripcion = "Mancuerna junto al pecho, pies al ancho de hombros. Baja con el pecho arriba y sube empujando con los talones.",
                        imagen = R.drawable.sentadillagoble
                    ),
                    Ejercicio(
                        nombre = "Zancadas",
                        series = "3 series x 10 a 12 repeticiones por pierna",
                        descripcion = "Da un paso al frente y baja hasta que ambas rodillas formen 90 grados. Vuelve empujando con el talón delantero.",
                        imagen = R.drawable.zancadas
                    ),
                    Ejercicio(
                        nombre = "Puente de glúteo",
                        series = "3 series x 15 repeticiones",
                        descripcion = "Acostado boca arriba con rodillas dobladas. Eleva la cadera apretando glúteos y baja lento.",
                        imagen = R.drawable.puentedegluteo
                    )
                )
            ),
            DiaRutina(
                "Jueves", "Yoga y movilidad",
                "Flujo suave y estiramientos", "30 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Saludo al sol",
                        series = "5 rondas lentas, 20 segundos",
                        descripcion = "Realiza una respiracion lenta. Ve a tu ritmo.",
                        imagen = R.drawable.relajacion
                    ),
                    Ejercicio(
                        nombre = "Perro boca abajo",
                        series = "3 series x 30 segundos",
                        descripcion = "Manos y pies en el piso con la cadera hacia arriba formando una V invertida. Estira espalda y piernas.",
                        imagen = R.drawable.perrobocaabajo
                    ),
                    Ejercicio(
                        nombre = "Postura del niño",
                        series = "1 minuto",
                        descripcion = "Sentado sobre los talones, lleva el pecho al piso con los brazos estirados al frente. Respira profundo y relaja.",
                        imagen = R.drawable.repirandolento
                    )
                )
            ),
            DiaRutina(
                "Viernes", "Tren superior",
                "Press · Remo · Curl · Tríceps", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Press con mancuernas",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Acostado en el banco, baja las mancuernas a la altura del pecho y empuja hacia arriba sin bloquear los codos.",
                        imagen = R.drawable.pressconmancuerna
                    ),
                    Ejercicio(
                        nombre = "Remo con mancuerna",
                        series = "3 series x 12 repeticiones por lado",
                        descripcion = "Una mano y rodilla en el banco, espalda recta. Jala la mancuerna hacia la cadera y baja lento.",
                        imagen = R.drawable.remoconmancuerna
                    ),
                    Ejercicio(
                        nombre = "Curl de bíceps",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Codos pegados al cuerpo. Sube las mancuernas sin balancear el torso y baja controlado.",
                        imagen = R.drawable.curldebiceps
                    ),
                    Ejercicio(
                        nombre = "Extensión de tríceps",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Sostén una mancuerna con ambas manos detrás de la cabeza. Estira los brazos hacia arriba y baja despacio.",
                        imagen = R.drawable.extensiondetriceps
                    )
                )
            )

        )
    ),
    Objetivo(
        R.drawable.cuerpo_completo, "Cuerpo completo", "Mejorar resistencia y condición física",
        "Si tu objetivo es trabajar todo el cuerpo, esta es tu rutina semanal",
        listOf(
            DiaRutina(

                "Lunes", "Cardio moderado",
                "Trote suave o bicicleta", "30 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Trote suave o bicicleta",
                        series = "25 minutos a ritmo constante",
                        descripcion = "Elige trotar o pedalear a un ritmo en el que puedas mantener una conversación.",
                        imagen = R.drawable.saliratrotar
                    ),
                    Ejercicio(
                        nombre = "Vuelta a la calma",
                        series = "5 minutos",
                        descripcion = "Baja el ritmo poco a poco y termina con estiramientos suaves de piernas.",
                        imagen = R.drawable.repirandolento
                    )
                )
            ),
            DiaRutina(
                "Martes", "Fuerza base",
                "Peso muerto · Press militar · Dominadas asistidas", "55 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Peso muerto",
                        series = "4 series x 12 repeticiones",
                        descripcion = "Mancuerna pegada a las piernas, espalda recta. Empuja el piso con los pies y extiende cadera y rodillas a la vez.",
                        imagen = R.drawable.pesomuerto
                    ),
                    Ejercicio(
                        nombre = "Press militar",
                        series = "4 series x 8 repeticiones",
                        descripcion = "De pie, barra a la altura de los hombros. Empuja hacia arriba con el abdomen firme y baja controlado.",
                        imagen = R.drawable.pressmilitar
                    ),
                    Ejercicio(
                        nombre = "Dominadas asistidas",
                        series = "3 series x 8 repeticiones",
                        descripcion = "Usa banda o máquina de asistencia. Sube hasta pasar la barbilla de la barra y baja lento.",
                        imagen = R.drawable.dominadasasistidas
                    )
                )
            ),
            DiaRutina(
                "Miercoles", "Movilidad",
                "Estiramientos y core", "25 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Estiramiento de cadera",
                        series = "2 series x 30 segundos por lado",
                        descripcion = "En posición de zancada profunda, lleva la cadera al frente y siente el estiramiento en la parte delantera.",
                        imagen = R.drawable.estiramientodecadera
                    ),
                    Ejercicio(
                        nombre = "Plancha",
                        series = "3 series x 30 segundos",
                        descripcion = "Antebrazos en el piso y cuerpo recto. Aprieta abdomen y glúteos.",
                        imagen = R.drawable.plancha
                    ),
                    Ejercicio(
                        nombre = "Bird dog",
                        series = "3 series x 10 repeticiones por lado",
                        descripcion = "En cuatro apoyos, estira el brazo y la pierna contraria a la vez. Mantén la espalda estable.",
                        imagen = R.drawable.birdog
                    )
                )
            ),
            DiaRutina(
                "Jueves", "Circuito total",
                "Burpees · Kettlebell · Cuerda", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Burpees",
                        series = "4 series x 10 repeticiones",
                        descripcion = "Baja a plancha, haz una flexión, recoge los pies y salta con los brazos arriba.",
                        imagen = R.drawable.burpees
                    ),
                    Ejercicio(
                        nombre = "Swing con kettlebell",
                        series = "4 series x 15 repeticiones",
                        descripcion = "Lleva la cadera atrás y lanza la pesa al pecho con el impulso de los glúteos.",
                        imagen = R.drawable.switc
                    ),
                    Ejercicio(
                        nombre = "Saltar la cuerda",
                        series = "4 series x 1 minuto",
                        descripcion = "Saltos cortos, muñecas sueltas y aterrizaje suave sobre la punta de los pies.",
                        imagen = R.drawable.saltandocuerda
                    )
                )
            )

        )
    ),
    Objetivo(
        R.drawable.fuerza_potencia, "Fuerza", "Más potencia y mayor rendimiento",
        "Si tu objetivo es ganar fuerza, esta es tu rutina de la semana",
        listOf(
            DiaRutina(
                "Lunes", "Empuje",
                "Press de banca · Press militar · Fondos", "55 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Press de banca",
                        series = "5 series x 5 repeticiones",
                        descripcion = "Omóplatos juntos y pies firmes. Baja la barra al pecho con control y empuja con potencia.",
                        imagen = R.drawable.pressdebanca
                    ),
                    Ejercicio(
                        nombre = "Press militar",
                        series = "4 series x 6 repeticiones",
                        descripcion = "De pie, abdomen firme. Empuja la barra sobre la cabeza sin arquear la espalda.",
                        imagen = R.drawable.pressmilitar
                    ),
                    Ejercicio(
                        nombre = "Fondos",
                        series = "3 series x 8 repeticiones",
                        descripcion = "En barras paralelas, baja hasta que los codos formen 90 grados y empuja hacia arriba.",
                        imagen = R.drawable.fondo
                    )
                )
            ),
            DiaRutina(
                "Martes", "Tirón",
                "Dominadas · Remo con barra · Curl", "55 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Dominadas",
                        series = "4 series x 6 repeticiones",
                        descripcion = "Agarre un poco más ancho que los hombros. Sube hasta pasar la barbilla de la barra y baja lento.",
                        imagen = R.drawable.dominadasasistidas
                    ),
                    Ejercicio(
                        nombre = "Remo con mancuerda",
                        series = "4 series x 6 repeticiones",
                        descripcion = "  apoya una mano y una rodilla en un banco manteniendo la espalda recta, y con el otro brazo jala el peso llevando el codo en diagonal hacia tu cadera (no hacia el pecho) contrayendo la espalda, para luego bajar de forma lenta y controlada.",
                        imagen = R.drawable.remoconmancuerna
                    ),
                    Ejercicio(
                        nombre = "Curl con barra",
                        series = "3 series x 8 repeticiones",
                        descripcion = "Codos pegados al cuerpo. Sube la barra sin balancear el torso y baja controlado.",
                        imagen = R.drawable.curlconbarr
                    )
                )
            ),
            DiaRutina(
                "Miércoles", "Descanso activo",
                "Caminata y estiramientos", "20 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Caminata suave",
                        series = "15 minutos",
                        descripcion = "Ritmo tranquilo para activar la circulación y recuperar los músculos.",
                        imagen = R.drawable.caminata_libre
                    ),
                    Ejercicio(
                        nombre = "Estiramiento de espalda y hombros",
                        series = "2 series x 30 segundos",
                        descripcion = "Estira cada zona con respiración profunda, sin rebotes ni dolor.",
                        imagen = R.drawable.estiramientodecadera
                    )
                )
            ),
            DiaRutina(
                "Jueves", "Pierna",
                "Sentadilla · Peso muerto · Prensa", "60 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Sentadilla",
                        series = "5 series x 5 repeticiones",
                        descripcion = "Barra en la espalda alta. Baja hasta paralelo con el pecho arriba y sube con fuerza.",
                        imagen = R.drawable.sentadillaconbarra
                    ),
                    Ejercicio(
                        nombre = "Peso muerto",
                        series = "4 series x 5 repeticiones",
                        descripcion = "Mancuerna pegada a las piernas y espalda neutra. Empuja el piso para levantar.",
                        imagen = R.drawable.pesomuerto
                    ),
                    Ejercicio(
                        nombre = "Prensa de pierna",
                        series = "3 series x 10 repeticiones",
                        descripcion = "Baja hasta 90 grados en las rodillas y empuja sin bloquearlas arriba.",
                        imagen = R.drawable.prensadepierna
                    )
                )
            ),
            DiaRutina(
                "Viernes", "Fuerza total",
                "Series pesadas de los básicos", "55 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Sentadilla pesada",
                        series = "3 series x 3 repeticiones",
                        descripcion = "Usa un peso exigente, que te deje 1 o 2 repeticiones en reserva. Pide a alguien que te cubra.",
                        imagen = R.drawable.sentadillaconbarra
                    ),
                    Ejercicio(
                        nombre = "Press militar",
                        series = "3 series x 3 repeticiones",
                        descripcion = "Controla la bajada y empuja con potencia. Siempre con un compañero que te asista.",
                        imagen = R.drawable.pressmilitar
                    ),
                    Ejercicio(
                        nombre = "Peso muerto pesado",
                        series = "3 series x 3 repeticiones",
                        descripcion = "Mantén la espalda recta y las mancuernas pegadas al cuerpo. Descansa 3 minutos entre series.",
                        imagen = R.drawable.pesomuerto
                    )
                )
            )
        )
    ),
    Objetivo(
        R.drawable.tren_superior, "Enfoque en Tren Superior", "Fortalecer y marcar tren superior",
        "Si tu objetivo es fortalecer y marcar tu tren superior, esta es tu rutina de la semana",
        listOf(
            DiaRutina(
                "Lunes", "Pecho y tríceps",
                "Press de banca · Fondos · Extensión de tríceps", "50 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Press militar",
                        series = "4 series x 10 repeticiones",
                        descripcion = " baja la barra al pecho y empuja sin bloquear los codos.",
                        imagen = R.drawable.pressmilitar
                    ),
                    Ejercicio(
                        nombre = "Fondos",
                        series = "3 series x 10 repeticiones",
                        descripcion = "En barras paralelas, baja hasta 90 grados en los codos y sube con control.",
                        imagen = R.drawable.fondo
                    ),
                    Ejercicio(
                        nombre = "Extensión de tríceps",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Sostén una mancuerna con ambas manos detrás de la cabeza y estira los brazos hacia arriba.",
                        imagen = R.drawable.extensiondetriceps
                    )
                )
            ),
            DiaRutina(
                "Martes", "Espalda y bíceps",
                "Dominadas · Remo con barra · Curl", "50 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Dominadas",
                        series = "4 series x 8 repeticiones",
                        descripcion = "Agarre ancho. Sube hasta pasar la barbilla de la barra y baja lento.",
                        imagen = R.drawable.dominadasasistidas
                    ),
                    Ejercicio(
                        nombre = "Remo con mancueran",
                        series = "4 series x 10 repeticiones",
                        descripcion = "Espalda recta y torso inclinado. Jala la mancuerna al abdomen juntando los omóplatos.",
                        imagen = R.drawable.remoconmancuerna
                    ),
                    Ejercicio(
                        nombre = "Curl de bíceps",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Codos pegados al cuerpo. Sube sin balancear y baja controlado.",
                        imagen = R.drawable.curldebiceps
                    )
                )
            ),
            DiaRutina(
                "Miércoles", "Descanso activo",
                "Caminata y movilidad de hombros", "20 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Caminata suave",
                        series = "15 minutos",
                        descripcion = "Ritmo cómodo para recuperar sin fatigar los músculos.",
                        imagen = R.drawable.caminata_libre
                    ),
                    Ejercicio(
                        nombre = "Círculos de hombro",
                        series = "2 series x 15 repeticiones hacia cada lado",
                        descripcion = "Brazos extendidos a los lados, dibuja círculos grandes y lentos.",
                        imagen = R.drawable.circulosdehombro
                    )

                )
            ),
            DiaRutina(
                "Jueves", "Hombros",
                "Press militar · Elevaciones laterales · Face pull", "45 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Press militar",
                        series = "4 series x 10 repeticiones",
                        descripcion = "De pie con el abdomen firme. Empuja la barra o las mancuernas sobre la cabeza sin arquear la espalda.",
                        imagen = R.drawable.pressmilitar
                    ),
                    Ejercicio(
                        nombre = "Elevaciones laterales",
                        series = "3 series x 15 repeticiones",
                        descripcion = "Sube las mancuernas a los lados hasta la altura de los hombros con los codos ligeramente flexionados.",
                        imagen = R.drawable.elevacioneslaterales
                    ),
                    Ejercicio(
                        nombre = "Face pull",
                        series = "3 series x 15 repeticiones",
                        descripcion = "Con la polea a la altura del rostro, jala la cuerda hacia la cara abriendo los codos.",
                        imagen = R.drawable.facefull
                    )
                )
            ),
            DiaRutina(
                "Viernes", "Brazos y core",
                "Curl martillo · Fondos en banco · Plancha", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Curl martillo",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Agarra las mancuernas con las palmas enfrentadas y sube sin mover los codos.",
                        imagen = R.drawable.curlmartillo
                    ),
                    Ejercicio(
                        nombre = "Fondos en banco",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Manos en el borde del banco y piernas estiradas. Baja los codos a 90 grados y empuja.",
                        imagen = R.drawable.fondo
                    ),
                    Ejercicio(
                        nombre = "Plancha",
                        series = "3 series x 40 segundos",
                        descripcion = "Cuerpo recto de cabeza a talones. Aprieta abdomen y glúteos.",
                        imagen = R.drawable.plancha
                    )
                )
            )

        )
    ),
    Objetivo(
        R.drawable.fuerza_potencia, "Calistenia", "Aprende a dominar tu propio peso",
        "Si quieres aprender a entrenar con el peso de tu cuerpo, esta es tu rutina de la semana",
        listOf(
            DiaRutina(
                "Lunes", "Empuje base",
                "Flexiones · Fondos en banco · Flexiones diamante", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Flexiones",
                        series = "4 series x 10 repeticiones",
                        descripcion = "Manos bajo los hombros y cuerpo recto. Baja el pecho cerca del piso y empuja.",
                        imagen = R.drawable.flexiones
                    ),
                    Ejercicio(
                        nombre = "Fondos en banco",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Manos en el borde del banco, baja los codos a 90 grados y empuja hacia arriba.",
                        imagen = R.drawable.fondo
                    )
                )
            ),
            DiaRutina(
                "Miércoles", "Piernas y core",
                "Sentadilla libre · Zancadas · Plancha · Elevación de piernas", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Sentadilla",
                        series = "4 series x 15 repeticiones",
                        descripcion = "Pies al ancho de hombros. Baja con el pecho arriba y sube empujando con los talones.",
                        imagen = R.drawable.sentadillaconbarra
                    ),
                    Ejercicio(
                        nombre = "Zancadas",
                        series = "3 series x 12 repeticiones por pierna",
                        descripcion = "Paso largo al frente y baja hasta 90 grados en ambas rodillas. Vuelve con el talón delantero.",
                        imagen = R.drawable.zancadas
                    ),
                    Ejercicio(
                        nombre = "Plancha",
                        series = "3 series x 40 segundos",
                        descripcion = "Cuerpo recto de cabeza a talones. Abdomen y glúteos firmes.",
                        imagen = R.drawable.plancha
                    ),
                    Ejercicio(
                        nombre = "Elevación de piernas",
                        series = "3 series x 12 repeticiones",
                        descripcion = "Acostado boca arriba, sube las piernas rectas y baja lento sin despegar la espalda baja.",
                        imagen = R.drawable.elevaciondepierna
                    )
                )
            ),
            DiaRutina(
                "Jueves", "Movilidad y equilibrio",
                "Estiramientos · Postura del cuervo · Paso al handstand en pared", "30 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Estiramientos",
                        series = "5 minutos",
                        descripcion = "Estira muñecas, hombros y cadera para preparar el cuerpo.",
                        imagen = R.drawable.estiramientodecadera
                    ),
                    Ejercicio(
                        nombre = "Postura del cuervo",
                        series = "4 intentos de 10 segundos",
                        descripcion = "Apoya las rodillas en la parte trasera de los brazos e inclínate hasta despegar los pies. Practica sobre una colchoneta.",
                        imagen = R.drawable.posturadelcuevo
                    ),
                    Ejercicio(
                        nombre = "Paso al handstand en pared",
                        series = "3 series x 20 segundos",
                        descripcion = "Sube los pies por la pared con el abdomen firme y los brazos rectos. Con calma y sin forzar.",
                        imagen = R.drawable.paso
                    )
                )
            ),
            DiaRutina(
                "Viernes", "Circuito total",
                "Burpees · Flexiones · Sentadillas con salto · Escaladores", "40 min",
                detalle = listOf(
                    Ejercicio(
                        nombre = "Burpees",
                        series = "4 series x 10 repeticiones",
                        descripcion = "Baja a plancha, haz una flexión, recoge los pies y salta con los brazos arriba.",
                        imagen = R.drawable.burpees
                    ),
                    Ejercicio(
                        nombre = "Flexiones",
                        series = "4 series x 12 repeticiones",
                        descripcion = "Cuerpo recto y pecho cerca del piso. Empuja con control.",
                        imagen = R.drawable.flexiones
                    ),
                    Ejercicio(
                        nombre = "Sentadillas con salto",
                        series = "4 series x 12 repeticiones",
                        descripcion = "Baja a sentadilla y salta con fuerza. Aterriza suave.",
                        imagen = R.drawable.sentadillaconsalto
                    ),
                    Ejercicio(
                        nombre = "Escaladores",
                        series = "4 series x 30 segundos",
                        descripcion = "En plancha alta, lleva las rodillas al pecho alternando rápido, sin subir la cadera.",
                        imagen = R.drawable.escaladores
                    )
                )
            )
        )
    )
)

val actividades = listOf(
    Actividad(
        imagen = R.drawable.saliratrotar,
        nombre = "Salir a trotar",
        mensaje = "Un trote al aire libre despeja la mente y cuida tu corazón",
        duracion = "30 min",
        fases = listOf(
            Fase("Calentamiento", "5 min", "Camina rápido para subir poco a poco el pulso."),
            Fase("Trote suave", "20 min", "Trota a un ritmo en el que puedas conversar sin ahogarte."),
            Fase("Vuelta a la calma", "5 min", "Camina despacio y estira piernas y cadera.")
        ),
        consejo = "Lleva agua y tómala en pequeños sorbos al terminar."
    ),
    Actividad(
        imagen = R.drawable.relajacion,
        nombre = "Relajar el cuerpo",
        mensaje = "Respira, estírate y suelta la tensión del día",
        duracion = "20 min",
        fases = listOf(
            Fase("Respiración profunda", "5 min", "Inhala por la nariz y exhala lento por la boca."),
            Fase("Estiramientos suaves", "10 min", "Cuello, hombros, espalda y piernas. Mantén cada postura 30 segundos."),
            Fase("Relajación final", "5 min", "Acuéstate boca arriba y suelta todo el cuerpo.")
        ),
        consejo = "Hazlo en un lugar tranquilo. Toma un poco de agua al terminar."
    ),
    Actividad(
        imagen = R.drawable.caminata_libre,
        nombre = "Caminata libre",
        mensaje = "Caminar a tu ritmo también cuenta como entrenar",
        duracion = "40 min",
        fases = listOf(
            Fase("Paso suave", "5 min", "Empieza tranquilo para activar piernas y respiración."),
            Fase("Ritmo constante", "30 min", "Camina a paso firme, con la espalda recta y los brazos relajados."),
            Fase("Vuelta a la calma", "5 min", "Baja el ritmo y estira suavemente las piernas.")
        ),
        consejo = "Lleva agua y tómala durante el recorrido."
    )
)

val estadosEnergia = listOf(
    EstadoEnergia(
        "😄", "Con Energía", "Desafío total", Verde,
        "¡Hoy es tu día! Aprovecha esa energía y dalo todo en tu rutina 💪"
    ),
    EstadoEnergia(
        "😐", "Normal", "Ritmo medio", Ambar,
        "Un paso a la vez también es avanzar. Ya empezar es ganar 🙌"
    ),
    EstadoEnergia(
        "😴", "Sin Energía", "Recuperación", Rojo,
        "Descansar también es entrenar. Haz algo suave y sé amable contigo 💛"
    )
)
// ---------------------------------------------------------------------
val retosFlash = listOf(
    RetoFlash(
        R.drawable.activacion, "Activación express", "5 min",
        listOf(
            DiaReto("Despierta el cuerpo", "Marcha en el lugar 2 minutos, círculos de brazos 1 minuto y 15 sentadillas."),
            DiaReto("Cadera activa", "Círculos de cadera 1 minuto, 20 sentadillas y 10 zancadas por pierna."),
            DiaReto("Core despierto", "Plancha 30 segundos, 20 abdominales y 20 segundos de escaladores."),
            DiaReto("Pecho y brazos", "10 flexiones (puedes apoyar las rodillas), círculos de brazos 1 minuto y plancha 30 segundos."),
            DiaReto("Piernas a tope", "25 sentadillas, 10 zancadas por pierna y 20 saltos suaves."),
            DiaReto("Ritmo alto", "3 rondas de 30 segundos de saltos de tijera y 30 segundos de marcha en el lugar."),
            DiaReto("Cierra la semana", "Repite tu día favorito del reto y termina con 1 minuto de estiramiento.")
        )
    ),
    RetoFlash(
        R.drawable.relajamiento_express, "Estiramiento salvavidas", "8 min",
        listOf(
            DiaReto("Cuello y hombros", "Inclina la cabeza a cada lado 30 segundos y gira los hombros 1 minuto. Sin rebotes."),
            DiaReto("Espalda alta", "Postura del niño 1 minuto y gato-vaca 10 repeticiones lentas."),
            DiaReto("Espalda baja", "Rodillas al pecho 1 minuto y torsión acostado 30 segundos por lado."),
            DiaReto("Cadera", "Zancada profunda 40 segundos por lado y postura de la paloma 40 segundos por lado."),
            DiaReto("Piernas", "Cuádriceps 30 segundos por pierna, isquios sentado 40 segundos y pantorrillas en la pared 30 segundos por lado."),
            DiaReto("Pecho y brazos", "Estiramiento de pecho en la pared 30 segundos por lado y tríceps 30 segundos por brazo."),
            DiaReto("Cuerpo completo", "Recorre todo: cuello, espalda, cadera y piernas, 1 minuto en cada zona.")
        )
    ),
    RetoFlash(
        R.drawable.cardio_relampago, "Cardio relámpago", "10 min",
        listOf(
            DiaReto("Prueba de ritmo", "2 minutos de trote en el lugar y 1 minuto de saltos de tijera. Repite 3 veces."),
            DiaReto("Saltos", "5 rondas de 40 segundos de saltos de tijera y 20 segundos de descanso, más 3 minutos de trote en el lugar."),
            DiaReto("Rodillas arriba", "5 rondas de 40 segundos de rodillas al pecho y 20 segundos de descanso, más 2 minutos de estiramiento."),
            DiaReto("Burpees suaves", "4 rondas de 8 burpees con 40 segundos de pausa activa entre rondas."),
            DiaReto("Escaladores", "5 rondas de 30 segundos de escaladores y 30 segundos de descanso, más 3 minutos de trote suave."),
            DiaReto("Todo junto", "Circuito de saltos de tijera, rodillas arriba, escaladores y burpees: 40 segundos cada uno, 3 rondas."),
            DiaReto("Máximo esfuerzo", "El circuito del día 6 con 45 segundos de trabajo y 15 de descanso. ¡Es tu gran final!")
        )
    )
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GIMNASIOTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = 	MaterialTheme.colorScheme.onSurface
                ) {
                    AppNavegacion()
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun GIMNASIO(
    onObjetivoClick: (Int) -> Unit = {},
    onActividadClick: (Int) -> Unit = {},
    onRetoClick: (Int) -> Unit = {},
    progresoRetos: List<Int> = listOf(0, 0, 0),
    favoritos: List<Int> = emptyList(),
    onFavoritoClick: (Int) -> Unit = {},
    onVerFavoritos: () -> Unit = {}
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

@Composable
fun PerfilHeroeCard(racha: Int = 0) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Redondeado)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
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
                        color = 	MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
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
                color = MaterialTheme.colorScheme.primary,
                trackColor = 	MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "650 / 1.000 XP", style = MaterialTheme.typography.bodySmall)
                Text(text = "🔥 Racha: $racha ${if (racha == 1) "día" else "días"}",
               style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
fun ObjetivoCard(
    objetivo: Objetivo,
    esFavorito: Boolean = false,
    onFavoritoClick: () -> Unit = {},
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(190.dp)
            .height(240.dp)
            .clip(Redondeado)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = objetivo.imagen),
            contentDescription = objetivo.nombre,
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

        BotonFavorito(
            esFavorito = esFavorito,
            onClick = onFavoritoClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
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
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
@Composable
fun ActividadCard(actividad: Actividad, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .clip(Redondeado)
            .clickable(onClick = onClick),
        shape = Redondeado,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
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
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Text(
                text = actividad.mensaje,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Composable
fun SemaforoItem(
    estado: EstadoEnergia,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = estado.color.copy(alpha = if (seleccionado) 0.45f else 0.18f),
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = estado.emoji, fontSize = 30.sp)
            Text(
                text = estado.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = estado.color
            )
            Text(
                text = estado.mision,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun RetoFlashCard(reto: RetoFlash, diasCompletados: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = reto.imagen),
                contentDescription = reto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$diasCompletados de ${reto.dias.size} días",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = reto.duracion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    val progresoRetos = remember { mutableStateListOf(0, 0, 0) }
    val favoritos = remember { mutableStateListOf<Int>() }
    val alternarFavorito: (Int) -> Unit = { indice ->
        if (favoritos.contains(indice)) favoritos.remove(indice) else favoritos.add(indice)
    }

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            GIMNASIO(
                onObjetivoClick = { indice -> navController.navigate("rutina/$indice") },
                onActividadClick = { indice -> navController.navigate("actividad/$indice") },
                onRetoClick = { indice -> navController.navigate("reto/$indice") },
                progresoRetos = progresoRetos,
                favoritos = favoritos,
                onFavoritoClick = alternarFavorito,
                onVerFavoritos = { navController.navigate("favoritos") }
            )
        }

            composable(
            route = "rutina/{indice}",
            arguments = listOf(navArgument("indice") { type = NavType.IntType })
        ) { backStackEntry ->
            val indice = backStackEntry.arguments?.getInt("indice") ?: 0
                RutinaScreen(
                    objetivo = objetivos[indice],
                    esFavorito = favoritos.contains(indice),
                    onFavoritoClick = { alternarFavorito(indice) },
                    onDiaClick = { indiceDia -> navController.navigate("dia/$indice/$indiceDia") },
                    onVolver = { navController.popBackStack() }
                )
        }

    composable(
        route = "reto/{indice}",
        arguments = listOf(navArgument("indice") { type = NavType.IntType })
    ) { backStackEntry ->
        val indice = backStackEntry.arguments?.getInt("indice") ?: 0
        RetoScreen(
            reto = retosFlash[indice],
            racha = progresoRetos.sum(),
            diasCompletados = progresoRetos[indice],
            onCompletarDia = { progresoRetos[indice] = progresoRetos[indice] + 1 },
            onReiniciar = { progresoRetos[indice] = 0 },
            onVolver = { navController.popBackStack() }
        )
    }

        composable("favoritos") {
            FavoritosScreen(
                favoritos = favoritos,
                onRutinaClick = { indice -> navController.navigate("rutina/$indice") },
                onQuitarFavorito = alternarFavorito,
                onVolver = { navController.popBackStack() }
            )
        }

        composable(
            route = "actividad/{indice}",
            arguments = listOf(navArgument("indice") { type = NavType.IntType })
        ) { backStackEntry ->
            val indice = backStackEntry.arguments?.getInt("indice") ?: 0
            ActividadScreen(
                actividad = actividades[indice],
                onVolver = { navController.popBackStack() }
            )
        }
        // NUEVA pantalla: ejercicios del día
        composable(
            route = "dia/{objetivo}/{dia}",
            arguments = listOf(
                navArgument("objetivo") { type = NavType.IntType },
                navArgument("dia") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val iObjetivo = backStackEntry.arguments?.getInt("objetivo") ?: 0
            val iDia = backStackEntry.arguments?.getInt("dia") ?: 0
            DiaScreen(
                objetivo = objetivos[iObjetivo],
                dia = objetivos[iObjetivo].dias[iDia],
                onVolver = { navController.popBackStack() }
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
fun DiaRutinaCard(dia: DiaRutina, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = dia.dia,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = dia.enfoque,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = dia.duracion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Text(
                text = dia.ejercicios,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Ver ejercicios ›",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


// ---------------------------------------------------------------------
// PASO 6. NUEVA PANTALLA DEL DÍA + TARJETA DE EJERCICIO (agrégalas al final)
// ---------------------------------------------------------------------
@Composable
fun DiaScreen(objetivo: Objetivo, dia: DiaRutina, onVolver: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column {
                Text(
                    text = "${dia.dia} · ${objetivo.nombre}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = dia.enfoque,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Duración: ${dia.duracion}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        if (dia.detalle.isEmpty()) {
            // Días que todavía no tienen ejercicios detallados
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(dia.ejercicios, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            "Rutina detallada próximamente",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(dia.detalle) { ejercicio ->
                EjercicioCard(ejercicio)
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
                Text("Volver a la semana")
            }
        }
    }
}

@Composable
fun EjercicioCard(ejercicio: Ejercicio) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Imagen del ejercicio (o un marcador si todavía no la tienes)
            if (ejercicio.imagen != null) {
                Image(
                    painter = painterResource(id = ejercicio.imagen),
                    contentDescription = ejercicio.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🏋️", fontSize = 48.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = ejercicio.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Etiqueta con series y repeticiones
            Text(
                text = ejercicio.series,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = ejercicio.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun RutinaScreen(
    objetivo: Objetivo,
    esFavorito: Boolean,
    onFavoritoClick: () -> Unit,
    onDiaClick: (Int) -> Unit,
    onVolver: () -> Unit
)  {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(Redondeado)
            ) {
                Image(
                    painter = painterResource(id = objetivo.imagen),
                    contentDescription = objetivo.nombre,
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


                BotonFavorito(
                    esFavorito = esFavorito,
                    onClick = onFavoritoClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = objetivo.nombre,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = objetivo.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            Text(
                text = objetivo.mensaje,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        itemsIndexed(objetivo.dias) { indiceDia, dia ->
            DiaRutinaCard(dia, onClick = { onDiaClick(indiceDia) })
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

@Composable
fun ActividadScreen(actividad: Actividad, onVolver: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(Redondeado)
            ) {
                Image(
                    painter = painterResource(id = actividad.imagen),
                    contentDescription = actividad.nombre,
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
                        text = actividad.nombre,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = actividad.mensaje,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        item {
            TituloSeccion("Tu plan", "Duración total: ${actividad.duracion}")
        }

        items(actividad.fases) { fase ->
            FaseCard(fase)
        }

        item { BloqueConsejo(actividad.consejo) }

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

@Composable
fun FaseCard(fase: Fase) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = fase.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = fase.duracion,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = fase.descripcion,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

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

// ---------------------------------------------------------------------
@Composable
fun DiaRetoCard(
    numero: Int,
    dia: DiaReto,
    completado: Boolean,
    esSiguiente: Boolean,
    onCompletar: () -> Unit
) {
    // Estado: recuerda si ya le diste "Iniciar" a este día
    var iniciado by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (completado)
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f)
            else
                MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Día $numero",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = when {
                        completado -> "✅ Completado"
                        esSiguiente && iniciado -> "⏱ En progreso"
                        esSiguiente -> "▶ Te toca"
                        else -> "🔒 Bloqueado"
                    },
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Text(
                text = dia.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = dia.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Solo el día que toca muestra botones
            if (esSiguiente) {
                Spacer(modifier = Modifier.height(10.dp))

                if (!iniciado) {
                    // Paso 1: Iniciar
                    Button(
                        onClick = { iniciado = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Iniciar")
                    }
                } else {
                    // Paso 2: Completado
                    Text(
                        text = "¡Dale con todo! Cuando termines, toca Completado.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            iniciado = false
                            onCompletar()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text("Completado")
                    }
                }
            }
        }
    }
}

fun normalizar(texto: String): String {
    val sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD)
        .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
    return sinTildes.lowercase()
}

// Devuelve las posiciones (índices) de los objetivos que coinciden
fun buscarObjetivos(consulta: String): List<Int> {
    val q = normalizar(consulta.trim())
    if (q.isEmpty()) return emptyList()

    val resultado = mutableListOf<Int>()

    for (i in objetivos.indices) {
        val objetivo = objetivos[i]

        var coincide = normalizar(objetivo.nombre).contains(q) ||
                normalizar(objetivo.descripcion).contains(q)

        // También busca dentro de los días y ejercicios
        for (dia in objetivo.dias) {
            if (normalizar(dia.enfoque).contains(q) ||
                normalizar(dia.ejercicios).contains(q)
            ) {
                coincide = true
            }
        }

        if (coincide) resultado.add(i)
    }
    return resultado
}

@Composable
fun ResultadoBusquedaItem(objetivo: Objetivo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = objetivo.imagen),
                contentDescription = objetivo.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = objetivo.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = objetivo.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "›",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
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

@Composable
fun FavoritoItem(objetivo: Objetivo, onClick: () -> Unit, onQuitar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = objetivo.imagen),
                contentDescription = objetivo.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = objetivo.nombre, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "${objetivo.dias.size} días · ${objetivo.descripcion}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            BotonFavorito(esFavorito = true, onClick = onQuitar)
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

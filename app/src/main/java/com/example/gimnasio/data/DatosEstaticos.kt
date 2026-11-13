package com.example.gimnasio.data

import com.example.gimnasio.R
import com.example.gimnasio.model.*
import com.example.gimnasio.ui.theme.Ambar
import com.example.gimnasio.ui.theme.Rojo
import com.example.gimnasio.ui.theme.Verde

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

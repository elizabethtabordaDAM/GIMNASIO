package com.example.gimnasio.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class Ejercicio(
    val nombre: String,
    val series: String,
    val descripcion: String,
    @DrawableRes val imagen: Int? = null
)

data class DiaRutina(
    val dia: String,
    val enfoque: String,
    val ejercicios: String,
    val duracion: String,
    val detalle: List<Ejercicio> = emptyList(),
    @DrawableRes val imagen: Int? = null,
    val instrucciones: String = ""
)

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
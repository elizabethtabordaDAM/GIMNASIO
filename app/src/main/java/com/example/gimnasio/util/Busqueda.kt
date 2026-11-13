package com.example.gimnasio.util

import com.example.gimnasio.data.objetivos
import java.text.Normalizer

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